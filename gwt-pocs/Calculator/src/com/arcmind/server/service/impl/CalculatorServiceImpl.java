package com.arcmind.server.service.impl;


import com.arcmind.client.server.CalculatorService;
import com.arcmind.client.model.Calculator;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:40:39
 */
public class CalculatorServiceImpl extends RemoteServiceServlet implements CalculatorService {

    Calculator calculator = new Calculator();

    public int add(int number1, int number2) {
        calculator.setFirstNumber(number1);
        calculator.setSecondNumber(number2);
        calculator.add();
        return calculator.getResult();
    }

    public int subtract(int number1, int number2) {
        calculator.setFirstNumber(number1);
        calculator.setSecondNumber((-1)*number2);
        calculator.add();
        return calculator.getResult();
    }

    public int multiply(int number1, int number2) {
        calculator.setFirstNumber(number1);
        calculator.setSecondNumber(number2);
        calculator.multiply();
        return calculator.getResult();
    }

    public int divide(int number1, int number2) {
        calculator.setFirstNumber(number1);
        calculator.setSecondNumber(number2);
        calculator.divide();
        return calculator.getResult();
    }
}
