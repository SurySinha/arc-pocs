package com.arcmind.contact.actions;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcmind.contact.form.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:26
 * To change this template use File | Settings | File Templates.
 */
public class UpdateAction extends Action {



    /**
     * Updates specified contact
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        ContactForm contactForm = (ContactForm)form;

        try {

            Contact contact = new Contact();
            bindFormToContact(contactForm, contact);

            // Business logic
            Contact result = ContactForm.persist(contact);

            if (result != null) {
                statusMessage(request,contact.toString());
            } else {
                errorMessage(request);
            }
        } catch (Exception e){
            errorMessage(request);
        } finally {
            saveForm(contactForm, request);
        }

        return mapping.findForward("success");
    }

    private void saveForm(ContactForm contactForm, HttpServletRequest request) {
        contactForm.setHideAddNewLink(false);
        contactForm.setRendered(false);
        contactForm.setPersistCommand("Update");
        request.setAttribute("contactForm", contactForm);
    }

    private void errorMessage(HttpServletRequest request) {
        ActionMessages errors = new ActionErrors();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.failure", null));
        saveErrors(request, errors);
    }

    private void statusMessage(HttpServletRequest request, String contactInfo) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.updated", contactInfo));
        saveMessages(request, messages);
    }

    private void bindFormToContact(ContactForm contactForm, Contact contact) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }
}
