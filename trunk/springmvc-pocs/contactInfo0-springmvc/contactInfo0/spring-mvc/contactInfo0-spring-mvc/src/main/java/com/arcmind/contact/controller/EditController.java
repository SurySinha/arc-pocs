package com.arcmind.contact.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.arcmind.contact.view.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 23.02.2008
 * Time: 18:11:14
 * To change this template use File | Settings | File Templates.
 */
public class EditController implements Controller {


    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ContactForm contactForm = new ContactForm();

        try {
            String id = httpServletRequest.getParameter("id");

            //Business logic
            Contact contact = contactForm.getContactById(Long.parseLong(id));
            saveContactToForm(contactForm, contact);
            saveIdToForm(contactForm, id);

            statusMessage(contactForm);
        } catch (Exception e) {
          errorMessage(contactForm);
        } finally {
          saveForm(contactForm);
        }

        return new ModelAndView("contacts", "contactForm", contactForm);

    }

    private void saveIdToForm(ContactForm contactForm, String id) {
        contactForm.setId(Long.parseLong(id));
    }

    private void saveContactToForm(ContactForm contactForm, Contact contact) {
        contactForm.setFirstName(contact.getFirstName());
        contactForm.setLastName(contact.getLastName());
        contactForm.setContact(contact);
    }

    private void saveForm(ContactForm contactForm) {
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);
        contactForm.setPersistCommand("Update");
    }

    private void errorMessage(ContactForm contactForm) {
        contactForm.setError("message.failure");
    }

    private void statusMessage(ContactForm contactForm) {
        contactForm.setMessage("message.read");
    }
}
