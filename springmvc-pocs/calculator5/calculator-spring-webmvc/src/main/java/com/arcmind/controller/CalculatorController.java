package com.arcmind.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.arcmind.view.CalculatorForm;
import com.arcmind.App;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 07.01.2008
 * Time: 20:39:35
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorController implements Controller {

    /**
     * Simple request handling
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse)
            throws Exception {
        return new ModelAndView("calculator", App.CALC_FORM, new CalculatorForm());
    }
}
