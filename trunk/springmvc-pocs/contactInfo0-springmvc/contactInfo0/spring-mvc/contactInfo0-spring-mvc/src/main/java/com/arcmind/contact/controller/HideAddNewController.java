package com.arcmind.contact.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcmind.contact.view.ContactForm;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 23.01.2008
 * Time: 13:27:07
 * To change this template use File | Settings | File Templates.
 */
public class HideAddNewController  implements Controller {


    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ContactForm contactForm = new ContactForm();
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);
        return new ModelAndView("contacts", "contactForm", contactForm);

    }
}
