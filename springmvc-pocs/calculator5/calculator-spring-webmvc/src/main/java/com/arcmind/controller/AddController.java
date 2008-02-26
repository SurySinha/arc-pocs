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
 * Time: 13:29:02
 * To change this template use File | Settings | File Templates.
 */
public class AddController extends SimpleFormController {

    Calculator calculator = new Calculator();

    /**
     * Sets Spring command object credentials
     */    
    public AddController() {
        setCommandName(App.CALC_FORM);
        setCommandClass(CalculatorForm.class);
    }

    /**
     * Method process success submit behavior
     * Called by framework after validation is succeeded
     * Adds 2 integer numbers, submitted by a  user
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

             calculator.add();
             calculatorForm.setResult(calculator.getResult());
             calculatorForm.setVisibleResult(true);
             statusMessage(calculatorForm);
         } catch (Exception e) {
             calculatorForm.setVisibleResult(false);
             errorMessage(calculatorForm);
             return new ModelAndView(getFormView(), App.CALC_FORM, calculatorForm);
         }

         return new ModelAndView(getSuccessView(), App.CALC_FORM, calculatorForm);
    }

    private void statusMessage(CalculatorForm calculatorForm) {
        calculatorForm.setMessage("message.added");
    }

    private void errorMessage(CalculatorForm calculatorForm) {
        calculatorForm.setError("message.failure");
    }

}
