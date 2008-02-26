package com.arcmind.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.*;
import com.arcmind.model.Calculator;
import com.arcmind.form.CalculatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 06.01.2008
 * Time: 16:31:29
 * To change this template use File | Settings | File Templates.
 */
public class MultiAction extends DispatchAction {

    /**
     *
     */
    Calculator calculator = new Calculator();

    /**
     * Adds 2 input numbers
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return resulting forward
     * @throws Exception
     */
    public ActionForward add(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        CalculatorForm calculatorForm = (CalculatorForm) form;
        try {
            before(calculatorForm);
            calculator.add();
            after(calculatorForm, request,"message.added");
        } catch (Exception ex) {
            handleError(calculatorForm, request);
        }

        return mapping.findForward("success");

    }

    /**
     * Multiplies 2 input numbers
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return resulting forward
     * @throws Exception
     */
    public ActionForward multiply(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        CalculatorForm calculatorForm = (CalculatorForm) form;
        try {
            before(calculatorForm);
            calculator.multiply();
            after(calculatorForm, request,"message.multiplied");
        } catch (Exception ex) {
            handleError(calculatorForm, request);
        }

        return mapping.findForward("success");

    }

    /**
     * Divides 2 integer numbers
     * If denominator equals zero, reset its value to 1
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward divide(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        CalculatorForm calculatorForm = (CalculatorForm) form;
        try {
            before(calculatorForm);
            calculator.divide();
            after(calculatorForm, request,"message.divided");
        } catch (Exception ex) {

            if (ex instanceof ArithmeticException) {// Zero divide?
                calculator.setSecondNumber(1);
                calculatorForm.setSecondNumber("1");
            }

            handleError(calculatorForm, request);
        }

        return mapping.findForward("success");
    }


    public ActionForward clear(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

        CalculatorForm calculatorForm = (CalculatorForm) form;
        try {
            before(calculatorForm);
            calculator.clear();
            after(calculatorForm, request,"message.cleared");
        } catch (Exception ex) {
            handleError(calculatorForm, request);
        } finally {
            calculatorForm.setFirstNumber("");
            calculatorForm.setSecondNumber("");
            calculatorForm.setVisibleResult(false);
        }

        return mapping.findForward("success");

    }


    /**
     * Helpers
     */
    private void statusMessage(HttpServletRequest request, String msgKey) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msgKey, null));
        saveMessages(request, messages);
    }

    private void errorMessage(HttpServletRequest request) {
        ActionMessages errors = new ActionErrors();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.failure", null));
        saveErrors(request, errors);
    }

    private void after(CalculatorForm calculatorForm, HttpServletRequest request, String msgKey) {
        calculatorForm.setResult(calculator.getResult());
        calculatorForm.setVisibleResult(true);
        statusMessage(request, msgKey);
    }

    private void before(CalculatorForm calculatorForm) {
        calculator.setFirstNumber(Integer.parseInt(calculatorForm.getFirstNumber()));
        calculator.setSecondNumber(Integer.parseInt(calculatorForm.getSecondNumber()));
    }

    private void handleError(CalculatorForm calculatorForm, HttpServletRequest request) {
        calculatorForm.setVisibleResult(false);
        errorMessage(request);
    }

}
