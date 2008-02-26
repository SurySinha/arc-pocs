package com.arcmind.view;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 08.01.2008
 * Time: 15:19:53
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorForm {

    /*
     * Generated fields
     */

    /** result property */
    private int result;

    /** secondNumber property */
    private String secondNumber;

    /** firstNumber property */
    private String firstNumber;

    /** visible property */
    private boolean visibleResult;

    /** success message  */
    private String message;

    /** error message  */
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }

    public boolean isVisibleResult() {
        return visibleResult;
    }

    public void setVisibleResult(boolean visibleResult) {
        this.visibleResult = visibleResult;
    }
}
