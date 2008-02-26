package com.arcmind.contact.view.validation;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import com.arcmind.contact.view.ContactForm;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 22.01.2008
 * Time: 22:28:01
 * To change this template use File | Settings | File Templates.
 */
public class ContactFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(ContactForm.class);
    }

    public void validate(Object command, Errors errors) {

        ContactForm contactForm = (ContactForm) command;  //bind command here to check for proper from type!
        contactForm.setHideAddNewLink(true);
        contactForm.setRendered(true);

        ValidationUtils.rejectIfEmpty(
                    errors,
                    "firstName",
                    "message.required",
                    null,
                    "errors.required");


            ValidationUtils.rejectIfEmpty(
                    errors,
                    "lastName",
                    "message.required",
                    null,
                    "errors.required");

    }

}
