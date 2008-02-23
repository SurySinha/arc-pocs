package com.arcmind.contact.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import com.arcmind.contact.model.Tag;
import com.arcmind.contact.model.TagRepository;

public class TagConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String value) {
        System.out.println("TagConverter.getAsObject");	    
		TagRepository repo = (TagRepository) facesContext
				.getExternalContext().getApplicationMap()
				.get("tagRepository");
		return repo.lookup(Long.valueOf(value));
	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
        System.out.println("TagConverter.getAsString");     	    
		return value == null ? "-1" : "" + ((Tag) value).getId();
	}

}