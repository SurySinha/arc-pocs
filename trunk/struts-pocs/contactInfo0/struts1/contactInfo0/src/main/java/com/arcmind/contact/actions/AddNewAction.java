package com.arcmind.contact.actions;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.arcmind.contact.form.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:10
 * To change this template use File | Settings | File Templates.
 */
public class AddNewAction extends Action {




    /**
     * Adds contact to repository
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

            statusMessage(result, contact, request);
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
        request.setAttribute("contactForm", contactForm);
    }

    private void errorMessage(HttpServletRequest request) {
        ActionMessages errors = new ActionErrors();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.failure", null));
        saveErrors(request, errors);
    }

    private void bindFormToContact(ContactForm contactForm, Contact contact) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }

    private void statusMessage(Contact result, Contact contact, HttpServletRequest request) {
        ActionMessages messages = new ActionMessages();

        if (result == null) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.added", contact));
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.updated", contact));
        }

        saveMessages(request, messages);
    }
}
