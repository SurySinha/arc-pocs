package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:56:48
 */
public interface CalculatorServiceAsync {
    public void add(int number1, int number2, AsyncCallback callback);
    public void subtract(int number1, int number2, AsyncCallback callback);
    public void multiply(int number1, int number2, AsyncCallback callback);
    public void divide(int number1, int number2, AsyncCallback callback);
    
}
