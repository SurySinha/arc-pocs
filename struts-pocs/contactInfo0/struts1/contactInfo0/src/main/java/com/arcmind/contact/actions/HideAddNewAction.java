package com.arcmind.contact.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arcmind.contact.form.ContactForm;
import com.arcmind.contact.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:02:10
 * To change this template use File | Settings | File Templates.
 */
public class HideAddNewAction extends Action {


    /**
     * Hides link in form
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
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);
        request.setAttribute("contactForm",contactForm);
        return mapping.findForward("success");
    }
}
