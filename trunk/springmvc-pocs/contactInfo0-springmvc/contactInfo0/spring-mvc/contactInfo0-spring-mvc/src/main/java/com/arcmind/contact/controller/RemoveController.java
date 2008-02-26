package com.arcmind.contact.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcmind.contact.view.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 23.01.2008
 * Time: 17:55:00
 * To change this template use File | Settings | File Templates.
 */
public class RemoveController implements Controller {


    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        ContactForm contactForm = new ContactForm();

           try {

               String id = httpServletRequest.getParameter("id");

               // Business logic
               Contact contact = contactForm.getContactById(Long.parseLong(id));
               contactForm.setContact(contact);
               contactForm.remove(contact);

               statusMessage(contactForm);
           } catch (Exception e) {
               errorMessage(contactForm);
           } finally {
               saveForm(contactForm);
           }

           return new ModelAndView("contacts", "contactForm", contactForm);
    }

    private void saveForm(ContactForm contactForm) {
          contactForm.setHideAddNewLink(false);
          contactForm.setRendered(false);
      }

      private void errorMessage(ContactForm contactForm) {
          contactForm.setError("message.failure");
      }

      private void statusMessage(ContactForm contactForm) {
          contactForm.setMessage("message.removed");
      }



}
