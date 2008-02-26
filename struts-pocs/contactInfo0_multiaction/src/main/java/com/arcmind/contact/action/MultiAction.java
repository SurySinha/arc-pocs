package com.arcmind.contact.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcmind.contact.form.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 20.01.2008
 * Time: 23:26:52
 * To change this template use File | Settings | File Templates.
 */
public class MultiAction extends DispatchAction {

    public ActionForward hideAddNew(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        ContactForm contactForm = (ContactForm)form;
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);
        contactForm.setPersistCommand("Add");
        request.setAttribute("contactForm",contactForm);
        return mapping.findForward("success");
    }

    public ActionForward addNew(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        ContactForm contactForm = (ContactForm)form;

        ActionMessages validationErrors = null;
        if ( ((validationErrors = contactForm.validate(mapping,request)) != null)) {
            if (!validationErrors.isEmpty()) {
                saveErrors(request, validationErrors);
                return mapping.findForward("success");
            }
        }

        try {

            Contact contact = new Contact();

            bindFormToContact(contact, contactForm);

            // Business logic
            Contact result = ContactForm.persist(contact);

            ActionMessages messages = new ActionMessages();

            if (result == null) {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.added", contact));
            } else {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.updated", contact));
            }

            saveMessages(request, messages);

        } catch (Exception e){
            errorMessage(request);

        } finally {
            contactForm.setHideAddNewLink(false);
            contactForm.setRendered(false);
            request.setAttribute("contactForm", contactForm);
            contactForm.setPersistCommand("Add");
        }

        return mapping.findForward("success");
    }

    private void bindFormToContact(Contact contact, ContactForm contactForm) {
        contact.setId(contactForm.getId());
        contact.setFirstName(contactForm.getFirstName());
        contact.setLastName(contactForm.getLastName());
    }

    private void errorMessage(HttpServletRequest request) {
        ActionMessages errors = new ActionErrors();
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.failure", null));
        saveErrors(request, errors);
    }

    public ActionForward edit(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        ContactForm contactForm = (ContactForm)form;

        try {
            String id = request.getParameter("id");

            //Business logic
            Contact contact = contactForm.getContactById(Long.parseLong(id));
            String contactInfo = contact.toString();

            saveContactToForm(contactForm, contact);
            statusMessage(request, contactInfo, "message.read");
        } catch (Exception e) {
            errorMessage(request);

        } finally {
            contactForm.setHideAddNewLink(true);
            contactForm.setRendered(true);
            contactForm.setPersistCommand("Update");
            request.setAttribute("contactForm", contactForm);
        }

        return mapping.findForward("success");
    }

    private void saveContactToForm(ContactForm contactForm, Contact contact) {
        contactForm.setFirstName(contact.getFirstName());
        contactForm.setLastName(contact.getLastName());
        contactForm.setContact(contact);
    }


    public ActionForward remove(ActionMapping mapping,
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

            statusMessage(request, contactInfo, "message.removed");
        } catch (Exception e) {
                errorMessage(request);
        } finally {
            contactForm.setHideAddNewLink(false);
            contactForm.setRendered(false);
            request.setAttribute("contactForm", contactForm);
        }

        return mapping.findForward("success");
    }

    public ActionForward update(ActionMapping mapping,
                                   ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
              throws Exception {

        ContactForm contactForm = (ContactForm)form;

        ActionMessages validationErrors = null;
        if ( ((validationErrors = contactForm.validate(mapping,request)) != null)) {
            if (!validationErrors.isEmpty()) {
                saveErrors(request, validationErrors);
                return mapping.findForward("success");
            }
        }        

        try {
            Contact contact = new Contact();
            bindFormToContact(contact, contactForm);

            // Business logic
            Contact result = ContactForm.persist(contact);

            if (result != null) {
                  statusMessage(request,contact.toString(),"message.updated");
            } else {
                  errorMessage(request);
            }
        } catch (Exception e){
              errorMessage(request);
        } finally {
            contactForm.setHideAddNewLink(false);
            contactForm.setRendered(false);
            contactForm.setPersistCommand("Update");
            request.setAttribute("contactForm", contactForm);
        }

          return mapping.findForward("success");
      }


    private void statusMessage(HttpServletRequest request, String contactInfo, String msgKey) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msgKey, contactInfo));
        saveMessages(request, messages);
    }


    

}
