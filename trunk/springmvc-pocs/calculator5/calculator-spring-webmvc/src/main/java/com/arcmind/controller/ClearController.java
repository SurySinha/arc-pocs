package com.arcmind.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import com.arcmind.model.Calculator;
import com.arcmind.view.CalculatorForm;
import com.arcmind.App;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 08.01.2008
 * Time: 13:38:48
 * To change this template use File | Settings | File Templates.
 */
public class ClearController implements Controller {

    Calculator calculator = new Calculator();

    /**
     * Clears view and model
     * Simple request handling
     * Includes messages output setup 
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        CalculatorForm calculatorForm = new CalculatorForm();

         try {
             calculator.clear();
             statusMessage(calculatorForm);
         } catch (Exception e) {
             calculatorForm.setVisibleResult(false);
             errorMessage(calculatorForm);             
         }

         return new ModelAndView("calculator", App.CALC_FORM, calculatorForm);
    }

    private void statusMessage(CalculatorForm calculatorForm) {
        calculatorForm.setMessage("message.cleared");
    }

    private void errorMessage(CalculatorForm calculatorForm) {
        calculatorForm.setError("message.failure");
    }

}
