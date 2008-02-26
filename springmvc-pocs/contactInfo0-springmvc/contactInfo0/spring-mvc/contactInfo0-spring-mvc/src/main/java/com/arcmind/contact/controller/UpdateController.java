package com.arcmind.contact.controller;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;


import com.arcmind.contact.model.Contact;
import com.arcmind.contact.view.ContactForm;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 23.01.2008
 * Time: 18:45:49
 * To change this template use File | Settings | File Templates.
 */
public class UpdateController extends SimpleFormController {

    public UpdateController() {
        setCommandName("contactForm");
        setCommandClass(ContactForm.class);
    }

    protected ModelAndView onSubmit(Object command, BindException bindException) throws Exception {

        ContactForm contactForm = (ContactForm)command;

        try {

            Contact contact = new Contact();
            bindFormToContact(contactForm, contact);

            // Business logic
            Contact result = ContactForm.persist(contact);
            contactForm.setContact(contact);

            if (result != null) {
                statusMessage(contactForm);
            } else {
                errorMessage(contactForm);
            }
        } catch (Exception e){
            errorMessage(contactForm);
        } finally {
            saveForm(contactForm);
        }

        return new ModelAndView(getSuccessView(), "contactForm", contactForm);
    }

    private void saveForm(ContactForm contactForm) {
        contactForm.setHideAddNewLink(false);
        contactForm.setRendered(false);
        contactForm.setPersistCommand("Update");
    }

    private void errorMessage(ContactForm contactForm) {
        contactForm.setError("message.failure");
    }

    private void statusMessage(ContactForm contactForm) {
       contactForm.setMessage("message.updated");
    }

    private void bindFormToContact(ContactForm contactForm, Contact contact) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }
}
