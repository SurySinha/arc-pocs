package com.arcmind.contact.controller;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;
import com.arcmind.contact.view.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 23.01.2008
 * Time: 13:27:17
 * To change this template use File | Settings | File Templates.
 */
public class AddNewController  extends SimpleFormController {


    public AddNewController() {
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
            
              statusMessage(result, contactForm);
          } catch (Exception e){
              contactForm.setError("message.failure");
          } finally {
            contactForm.setHideAddNewLink(false);
            contactForm.setRendered(false);
          }

        return new ModelAndView(getSuccessView(), "contactForm", contactForm);

    }

    private void bindFormToContact(ContactForm contactForm, Contact contact) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }

    private void statusMessage(Contact result, ContactForm contactForm) {
           if (result == null) {
               contactForm.setMessage("message.added");
           } else {
               contactForm.setMessage("message.updated");
           }
       }

}
