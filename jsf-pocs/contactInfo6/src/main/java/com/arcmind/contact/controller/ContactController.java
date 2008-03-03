package com.arcmind.contact.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.arcmind.contact.model.Contact;
import com.arcmind.contact.model.ContactRepository;
import com.arcmind.contact.model.Group;
import com.arcmind.contact.model.GroupRepository;
import com.arcmind.contact.model.Tag;
import com.arcmind.contact.model.TagRepository;

@SuppressWarnings("serial")
public class ContactController extends AbstractCrudController{
	/** Contact Controller collaborates with contactRepository. */
	private ContactRepository contactRepository;
	
	/** The current contact that is being edited. */
	private Contact contact = new Contact();
	
	/** Contact to remove. */
	private Contact selectedContact;
	
	/** The current form. */
	private UIForm form;
	
	/** Add new link. */
	private UICommand addNewCommand;
	
	/** Persist command. */
	private UICommand persistCommand;

	private GroupRepository groupRepository;
	
	private TagRepository tagRepository;
	
	
	/** For injection of collaborator. */
	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	/** For injection of collaborator. */
	public void setGroupRepository(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}
	/** For injection of collaborator. */
	public void setTagRepository(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public void addNew() {
	    System.out.println("ContactController.addNew()");
		form.setRendered(true);
		addNewCommand.setRendered(false);
		persistCommand.setValue("Add");
		super.setEdit(true);
	}
	public String persist() {
        System.out.println("ContactController.persist()");	    
		
		
		/* Turn form off, turn link on. */
		form.setRendered(false);
		addNewCommand.setRendered(true);
		
		
		/* Add a status message. */
		if (contactRepository.persist(contact) == null) {
			addStatusMessage("Added " + contact);
		} else {
			addStatusMessage("Updated " + contact);
		}
		super.setEdit(false);
		return "contactPersisted";
	}
	public void remove() {
        System.out.println("ContactController.remove()");	    
		contactRepository.remove(selectedContact);
		addStatusMessage("Removed " + selectedContact);	
	}
	public void read() {
        System.out.println("ContactController.read()");       
	    
		/* Prepare selected contact. */
		contact = selectedContact;
		
		/* Turn form on and the link off. */
		form.setRendered(true);
		addNewCommand.setRendered(false);
				
		addStatusMessage("Read " + contact);
		persistCommand.setValue("Update");
		super.setEdit(true);
	}
	private void addStatusMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, message, null));
	}
	
	public List<Contact> getContacts() {
        System.out.println("ContactController.getContacts()");
		return contactRepository.list();
	}

	public List<SelectItem> getAvailableGroups() {
        System.out.println("ContactController.getAvailableGroups()");
		List<Group> groups = groupRepository.list();
		List<SelectItem> list = new ArrayList<SelectItem>(groups.size()+1);
		list.add(new SelectItem(Long.valueOf(-1L), "select one"));
		for (Group group : groups) {
			SelectItem selectItem = new SelectItem(group, group.getName());
			list.add(selectItem);
		}
		return list;
	}
	
	public List<SelectItem> getAvailableTags() {
        System.out.println("ContactController.getAvailableTags()");
	    List<Tag> tags = tagRepository.list();
		List<SelectItem> list = new ArrayList<SelectItem>(tags.size());
		for (Tag tag : tags) {
			SelectItem selectItem = new SelectItem(tag, tag.getName());
			list.add(selectItem);
		}
		return list;
	}
	
	public void cancel () {
        System.out.println("ContactController.cancel()");	    
		form.setRendered(false);
		addNewCommand.setRendered(true);		
	}

	/* ------------------- Just properties. ------------------------- */
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public UIForm getForm() {
		return form;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public UICommand getAddNewCommand() {
		return addNewCommand;
	}

	public void setAddNewCommand(UICommand addNewCommand) {
		this.addNewCommand = addNewCommand;
	}

	public Contact getSelectedContact() {
		return selectedContact;
	}

	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
	}	

	public UICommand getPersistCommand() {
		return persistCommand;
	}

	public void setPersistCommand(UICommand persistCommand) {
		this.persistCommand = persistCommand;
	}
    @Override
    Object getFormObject() {
        if (super.isEdit()) {
            return this.contact;
        } else {
            return null;
        }
    }
	
}
