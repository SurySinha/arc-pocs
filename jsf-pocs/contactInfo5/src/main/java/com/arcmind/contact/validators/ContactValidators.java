package com.arcmind.contact.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.arcmind.contact.model.PhoneNumber;


public class ContactValidators {

    public void validatePhone(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        PhoneNumber phoneNumber = (PhoneNumber)value; 

        if (!phoneNumber.getAreaCode().equals("520")
            && !phoneNumber.getAreaCode().equals("602")) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Arizona residents only");
            message.setSummary("Arizona residents only");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

}
