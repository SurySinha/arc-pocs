package com.arcmind.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.arcmind.model.Contact;
import com.arcmind.form.ContactForm;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 12.01.2008
 * Time: 16:11:27
 * To change this template use File | Settings | File Templates.
 */
public class AddAction extends ActionSupport  {

     ContactForm contactForm;

     public ContactForm getContactForm() {
         return contactForm;
     }

     public void setContactForm(ContactForm contactForm) {
         this.contactForm = contactForm;
     }

     public String Add() throws Exception {

         
        try {
            Contact contact = new Contact();

            contact.setFirstName(contactForm.getFirstName());
            contact.setLastName(contactForm.getLastName());

            // Business logic
            ContactForm.persist(contact);

            statusMessage(contact);
        } catch (Exception ex) {
            errorMessage();
            return ERROR;
        } finally {
            saveForm(contactForm);
        }

        return SUCCESS;

    }

    private void errorMessage() {
        addActionError(getText("message.failure"));
    }

    private void statusMessage(Contact contact) {
        String args[] = {contact.toString()};
        addActionMessage(getText("message.added",args));
    }

    private void saveForm(ContactForm contactForm) {
         contactForm.setHideAddNewLink(false);
         contactForm.setRendered(false);
     }



}
