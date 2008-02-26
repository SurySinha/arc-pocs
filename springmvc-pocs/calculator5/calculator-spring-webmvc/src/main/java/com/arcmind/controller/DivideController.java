package com.arcmind.controller;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;
import com.arcmind.model.Calculator;
import com.arcmind.view.CalculatorForm;
import com.arcmind.App;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 08.01.2008
 * Time: 13:38:38
 * To change this template use File | Settings | File Templates.
 * Note: controller uses validator, which is different to CalculatorFormValidator
 */
public class DivideController extends SimpleFormController {

    Calculator calculator = new Calculator();

    /**
     * Sets Spring command object credentials
     */    
    public DivideController() {
        setCommandName(App.CALC_FORM);
        setCommandClass(CalculatorForm.class);
    }

    /**
     * Method process success submit behavior
     * Called by framework after validation is succeeded
     * Divides 2 integer numbers, submitted by a user
     * Outputs messages and errors
     * 
     * @param command
     * @param bindException
     * @return
     * @throws Exception
     */
     protected ModelAndView onSubmit(Object command, BindException bindException) throws Exception {
         CalculatorForm calculatorForm = (CalculatorForm) command;

         try {
             calculator.setFirstNumber(Integer.parseInt(calculatorForm.getFirstNumber()));
             calculator.setSecondNumber(Integer.parseInt(calculatorForm.getSecondNumber()));

             calculator.divide();
             calculatorForm.setResult(calculator.getResult());
             calculatorForm.setVisibleResult(true);
             statusMessage(calculatorForm);
         } catch (Exception e) {
             //Zero division we dont check here,
             // its being checked already in corresponding validator! 
             calculatorForm.setVisibleResult(false);
             errorMessage(calculatorForm);
             return new ModelAndView(getFormView(), App.CALC_FORM, calculatorForm);
         }

         return new ModelAndView(getSuccessView(), App.CALC_FORM, calculatorForm);
    }

    private void statusMessage(CalculatorForm calculatorForm) {
        calculatorForm.setMessage("message.divided");
    }

    private void errorMessage(CalculatorForm calculatorForm) {
        calculatorForm.setError("message.failure");
    }

}
