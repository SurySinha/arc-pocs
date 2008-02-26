package com.arcmind.contact.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.web.jsf.FacesContextUtils;

import com.arcmind.contact.model.Tag;
import com.arcmind.contact.model.TagRepository;

public class TagConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
		TagRepository repo = (TagRepository) FacesContextUtils.getWebApplicationContext(facesContext).getBean("tagRepository");
		return repo.lookup(Long.valueOf(value));
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		return value == null ? "-1" : "" + ((Tag) value).getId();
	}

}