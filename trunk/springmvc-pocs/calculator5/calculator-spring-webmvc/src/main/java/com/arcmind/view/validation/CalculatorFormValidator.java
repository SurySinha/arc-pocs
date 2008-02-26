package com.arcmind.view.validation;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import com.arcmind.view.CalculatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 08.01.2008
 * Time: 15:42:18
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(CalculatorForm.class);
    }

    /**
     * Validates submitted input values "firstNumber" and "secondNumber"
     * Behavior is standard, first binding occurs
     * Fails when parameters are empty or not integers
     * Sets corresponding error messages for output view 
     *
     * @param command
     * @param errors
     */
    public void validate(Object command, Errors errors) {
        CalculatorForm calculatorForm = (CalculatorForm) command;

        ValidationUtils.rejectIfEmpty(
                errors,
                "firstNumber",
                "errors.required",
                getMessageArguments("label.firstNumber"),
                "errors.required");


        ValidationUtils.rejectIfEmpty(
                errors,
                "secondNumber",
                "errors.required",
                getMessageArguments("label.secondNumber"),
                "errors.required");

        String firstNumber =  calculatorForm.getFirstNumber();

        try {
            if ((firstNumber!= null) && (!firstNumber.equals("")) )
                Integer.parseInt(firstNumber);
        } catch (NumberFormatException e) {
            errors.rejectValue(
                    "firstNumber",
                    "errors.integer",
                    getMessageArguments("label.firstNumber"),
                    "errors.integer");
        }

        String secondNumber =  calculatorForm.getSecondNumber();

        try {
            if ((secondNumber!= null) && (!secondNumber.equals("")) )
                Integer.parseInt(secondNumber);
        } catch (NumberFormatException e) {
            errors.rejectValue(
                    "secondNumber",
                    "errors.integer",
                    getMessageArguments("label.secondNumber"),
                    "errors.integer");
        }
    }

    // Helper method
    protected Object[] getMessageArguments(String key) {
        return new Object[]{new DefaultMessageSourceResolvable(new String[] {key})};
    }
}
