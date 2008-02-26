package com.arcmind.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.arcmind.form.ContactForm;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 12.01.2008
 * Time: 16:11:27
 * To change this template use File | Settings | File Templates.
 */
public class HideAddNewAction extends ActionSupport {

     ContactForm contactForm;

     public ContactForm getContactForm() {
         return contactForm;
     }

     public void setContactForm(ContactForm contactForm) {
         this.contactForm = contactForm;
     }


    public String HideAddNew() throws Exception {
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);
        return SUCCESS;
    }


}
