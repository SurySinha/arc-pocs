package com.arcmind.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.arcmind.form.ContactForm;
import com.arcmind.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:33
 * To change this template use File | Settings | File Templates.
 */
public class RemoveAction extends ActionSupport {

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }

    ContactForm contactForm;

    /**
     * Removes specified contact
     */
    public String Remove() throws Exception {

        try {

            Long id = contactForm.getId();

            // Business logic
            Contact contact = contactForm.getContactById(id);
            String contactInfo = contact.toString();//backup before removing!
            contactForm.remove(contact);

            statusMessage(contactInfo);
        } catch (Exception e) {
            errorMessage();
        } finally {
            saveForm(contactForm);
        }

        return SUCCESS;
    }

    private void saveForm(ContactForm contactForm) {
        contactForm.setHideAddNewLink(false);
        contactForm.setRendered(false);        
    }

    private void errorMessage() {
        addActionError(getText("message.failure"));
    }

    private void statusMessage(String contactInfo) {
        String args[] = {contactInfo};
        addActionMessage(getText("message.removed",args));
    }
}
