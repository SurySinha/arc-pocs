package com.arcmind.contact.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.application.FacesMessage;

import org.springframework.web.jsf.FacesContextUtils;

import com.arcmind.contact.model.Group;
import com.arcmind.contact.model.GroupRepository;

public class GroupConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
	    System.out.println("GroupConverter.getAsObject");
		GroupRepository repo = (GroupRepository) FacesContextUtils.getWebApplicationContext(facesContext).getBean("groupRepository");
	
		Long id = Long.valueOf(value);
		if (id == -1L) {
			throw new ConverterException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "required", "required"));
		}
		return repo.lookup(id);
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
	    System.out.println("GroupConverter.getAsString");	    
		return value == null ? "-1" : "" + ((Group) value).getId();
	}

}