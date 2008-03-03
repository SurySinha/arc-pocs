package com.arcmind.contact.converter;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.arcmind.contact.model.PhoneNumber;

/**
 * @author Richard Hightower
 * 
 */
public class PhoneConverter implements Converter {
    private static Pattern phoneMask;
    static {
        String countryCode = "^[0-9]{1,2}";
        String areaCode = "( |-|\\(){1,2}[0-9]{3}( |-|\\)){1,2}";
        String prefix = "( |-)?[0-9]{3}";
        String number = "( |-)[0-9]{4}$";
        phoneMask = Pattern.compile(countryCode + areaCode + prefix + number);
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        System.out.println("PhoneConverter.getAsObject()");

        if (value.trim().length() == 0) {
            return null;
        }
        /* Before we parse, let's see if it really is a phone number. */
        if (!phoneMask.matcher(value).matches()) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Phone number not valid");
            message.setSummary("Phone number not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }
        
        /* Now let's parse the string and populate a phone number object. */
        PhoneNumber phone = new PhoneNumber();
        phone.setOriginal(value);
        String[] phoneComps = value.split("[ ,()-]");
        String countryCode = phoneComps[0];
        phone.setCountryCode(countryCode);

        if ("1".equals(countryCode) && phoneComps.length == 4) {
            phone.setAreaCode(phoneComps[1]);
            phone.setPrefix(phoneComps[2]);
            phone.setNumber(phoneComps[3]);
        } else if ("1".equals(countryCode) && phoneComps.length != 4) {
            throw new ConverterException(new FacesMessage(
                    "No Soup for you butter fingers!"));
        } else if (phoneComps.length == 1 && value.length() > 10){
            phone.setCountryCode(value.substring(0,1));
            phone.setAreaCode(value.substring(1,4));
            phone.setPrefix(value.substring(4,7));
            phone.setNumber(value.substring(7));            
        } else {
            phone.setNumber(value);
        }
        return phone;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        System.out.println("PhoneConverter.getAsString()");
        return value.toString();
    }
}
