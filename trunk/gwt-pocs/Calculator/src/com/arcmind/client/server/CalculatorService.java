package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:35:10
 */
public interface CalculatorService extends RemoteService {
    public int add(int number1, int number2);
    public int subtract(int number1, int number2);
    public int multiply(int number1, int number2);
    public int divide(int number1, int number2);
}
