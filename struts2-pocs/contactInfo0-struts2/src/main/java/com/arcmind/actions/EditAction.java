package com.arcmind.actions;

import com.arcmind.form.ContactForm;
import com.arcmind.model.Contact;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:19
 * To change this template use File | Settings | File Templates.
 */
public class EditAction extends ActionSupport {

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }

    ContactForm contactForm;

    /**
     *
     * @return
     * @throws Exception
     */
    public String Edit() throws Exception {

        try {

            Long id = contactForm.getId();

            //Business logic
            Contact contact = contactForm.getContactById(id);

            String contactInfo = contact.toString();
            saveContactToForm(contactForm, contact);

            statusMessage(contactInfo);
        } catch (Exception e) {
            errorMessage();
        } finally {
          saveForm(contactForm);
        }

        return SUCCESS;

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

    private void errorMessage() {
        addActionError(getText("message.failure"));
    }

    private void statusMessage(String contactInfo) {
        String args[] = {contactInfo};
        addActionMessage(getText("message.read",args));
    }
}
