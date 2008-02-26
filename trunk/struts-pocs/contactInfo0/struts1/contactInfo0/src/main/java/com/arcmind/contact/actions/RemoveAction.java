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
 * Time: 23:02:33
 * To change this template use File | Settings | File Templates.
 */
public class RemoveAction extends Action {


    /**
     * Removes specified contact
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
                                 HttpServletResponse response) throws Exception {

        ContactForm contactForm = (ContactForm)form;

        try {

            String id = request.getParameter("id");

            // Business logic
            Contact contact = contactForm.getContactById(Long.parseLong(id));
            String contactInfo = contact.toString();//backup before removing!
            contactForm.remove(contact);

            statusMessage(contactInfo, request);
        } catch (Exception e) {
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

    private void statusMessage(String contactInfo, HttpServletRequest request) {
        ActionMessages messages = new ActionMessages();

        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.removed", contactInfo));

        saveMessages(request, messages);
    }
}
