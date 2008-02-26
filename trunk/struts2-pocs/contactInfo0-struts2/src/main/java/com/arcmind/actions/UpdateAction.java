package com.arcmind.actions;

import com.arcmind.form.ContactForm;
import com.arcmind.model.Contact;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:26
 * To change this template use File | Settings | File Templates.
 */
public class UpdateAction extends ActionSupport {

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }

    ContactForm contactForm;


    /**
     * Updates specified contact
     * 
     */
    public String Update()
            throws Exception {

        try {

            Contact contact = new Contact();
            bindFormToContact(contactForm, contact);

            // Business logic
            Contact result = ContactForm.persist(contact);

            if (result != null) {
                statusMessage(contact.toString());
            } else {
                errorMessage();
            }
        } catch (Exception e){
          errorMessage();
        } finally {
          saveForm(contactForm);
        }

        return SUCCESS;
    }

    private void saveForm(ContactForm contactForm) {
        contactForm.setHideAddNewLink(false);
        contactForm.setRendered(false);
        contactForm.setPersistCommand("Update");
    }

    private void errorMessage() {
        addActionError(getText("message.failure"));
    }

    private void statusMessage(String contactInfo) {
        String args[] = {contactInfo};
        addActionMessage(getText("message.updated",args));
    }

    private void bindFormToContact(ContactForm contactForm, Contact contact) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }
}
