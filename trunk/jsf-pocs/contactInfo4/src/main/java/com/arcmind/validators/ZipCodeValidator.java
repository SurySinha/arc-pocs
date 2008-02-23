/*
 * Created on May 22, 2004
 *
 */
package com.arcmind.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Richard Hightower
 * 
 */
public class ZipCodeValidator implements Validator {

    /** Accepts zip codes like 85710 */
    private static final String ZIP_REGEX = "[0-9]{5}";

    /** Optionally accepts a plus 4 */
    private static final String PLUS4_OPTIONAL_REGEX = "([ |-]{1}[0-9]{4})?";

    private static Pattern mask = null;

    static {
        mask = Pattern.compile(ZIP_REGEX + PLUS4_OPTIONAL_REGEX);
    }

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        System.out.println("ZipCodeValidator.validate()");

        /* Get the string value of the current field */
        String zipField = (String) value;

        /* Check to see if the value is a zip code */
        Matcher matcher = mask.matcher(zipField);

        if (!matcher.matches()) {

            FacesMessage message = new FacesMessage();
            message.setDetail("Zip code not valid");
            message.setSummary("Zip code not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
