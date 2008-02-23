package com.arcmind.contact.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.arcmind.contact.model.Validateable;
import com.arcmind.contact.model.ValidationException;

@SuppressWarnings("serial")
public abstract class AbstractCrudController implements Serializable {
    
    private boolean edit;
	
	public PhaseListener phaseListener = new PhaseListener() {
		
		public void afterPhase(PhaseEvent event) {
		    validate();
		}

		public void beforePhase(PhaseEvent event) {
			
		}

		public PhaseId getPhaseId() {
			return PhaseId.UPDATE_MODEL_VALUES;
		}
		
	};
	
	abstract Object getFormObject(); //subclass defines this
	
	private void validate() {
	    Object form = getFormObject();
	    if (! (form instanceof Validateable) || form == null) {
	        return;
	    }
		Validateable validateable = (Validateable) form;
		try {
			validateable.validate(); //validate object
		} catch (ValidationException validationException) {
			
			FacesContext.getCurrentInstance().renderResponse(); //Do not invoke application.
			addErrorMessage(validationException.getMessage());
			
		}
	}

	public PhaseListener getPhaseListener() {
		return phaseListener;
	}

	
    protected void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, message, null));
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
	
}
