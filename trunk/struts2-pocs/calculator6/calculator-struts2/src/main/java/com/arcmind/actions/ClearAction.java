package com.arcmind.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.arcmind.model.Calculator;
import com.arcmind.form.CalculatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 12.01.2008
 * Time: 16:11:27
 * To change this template use File | Settings | File Templates.
 */
public class ClearAction extends ActionSupport  {
     Calculator calculator = new Calculator();
     CalculatorForm calculatorForm;

     public CalculatorForm getCalculatorForm() {
         return calculatorForm;
     }

     public void setCalculatorForm(CalculatorForm calculatorForm) {
         this.calculatorForm = calculatorForm;
     }

     public String Clear() throws Exception {

         
        try {
            calculatorForm = new CalculatorForm();

            calculator.clear();
            statusMessage();
        } catch (Exception ex) {
            errorMessage();
            return ERROR;
        }

        return SUCCESS;

    }

    private void errorMessage() {
        addActionError(getText("message.failure"));
    }

    private void statusMessage() {
        addActionMessage(getText("message.cleared"));
    }

}
