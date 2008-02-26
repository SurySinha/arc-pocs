package com.arcmind.view.validation;

import org.springframework.validation.Errors;
import com.arcmind.view.CalculatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 10.01.2008
 * Time: 12:07:31
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorFormDivideValidator extends CalculatorFormValidator {


    /**
     * Validates input parameters to be valid: non-empty and integers
     * Additionally validates denominator not to be zero
     * Sets corresponding error messages for output view
     * 
     * @param command
     * @param errors
     */
    public void validate(Object command, Errors errors) {
        super.validate(command, errors);

        // Additionally validate denominator!
        if (errors.getErrorCount()==0) {
            CalculatorForm calculatorForm = (CalculatorForm) command;
            
            String secondNumber =  calculatorForm.getSecondNumber();
            if (secondNumber.trim().equals("0")){
                errors.rejectValue(
                    "secondNumber",
                    "errors.zerodivision",
                    getMessageArguments("label.secondNumber"),
                    "errors.integer");
            }
        }
    }
}
