package com.arcmind.form;

import com.arcmind.model.Contact;
import com.arcmind.model.ContactRepository;

import java.util.List;
import java.util.Iterator;


public class ContactForm {

    public ContactForm() {
        super();
        setPersistCommand("Add");
    }

    /*
	 * Generated fields
	 */

    /** Contact form collaborates with contactRepository. */
     public static ContactRepository contactRepository = new ContactRepository();

     /** The current contact that is being edited. */
     private Contact contact = new Contact();

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getPersistCommand() {
        return persistCommand;
    }

    public void setPersistCommand(String persistCommand) {
        this.persistCommand = persistCommand;
    }

    private String persistCommand;

    

    /** result property */
	private int result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /** result property */
	private long id;


    /** lastName property */
	private String lastName;

	/** firstName property */
	private String firstName;


    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isHideAddNewLink() {
        return hideAddNewLink;
    }

    public void setHideAddNewLink(boolean hideAddNewLink) {
        this.hideAddNewLink = hideAddNewLink;
    }

    private boolean rendered;
     private boolean hideAddNewLink;


    /**
	 * Returns the result.
	 * @return int
	 */
	public int getResult() {
		return result;
	}

	/** 
	 * Set the result.
	 * @param result The result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
        this.lastName = lastName.trim();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
	}

    // Static methods

     /**
      * Wraps repository persist command
      * Used for adding of modifying specified contact
      *
       * @param contact
      * @return
      */
     public static synchronized Contact persist(Contact contact) {
         return contactRepository.persist(contact);
     }


       // Helpers //

     /**
      * Helper method used in edit action
      * Reads contact
      * @param id
      * @return
      */
     public Contact getContactById(long id) {
         List contacts = contactRepository.getContacts();
         Iterator i = contacts.iterator();
         while (i.hasNext()){
             Contact c = (Contact)i.next();
             if (c.getId() == id){
                 return c;
             }
         }
         return null;
     }

     /**
      * Helper method used in remove action
      * Removed specified contact
      * @param contact
      */
     public void remove(Contact contact){
         contactRepository.remove(contact);
     }


     /**
      * Helper method
      * Obtains contacts collection
      * Used in JSP page for JSTL iteration
      * @return contacts collection
      */
     public List<Contact> getRecords() {
         return contactRepository.getContacts();
     }

    /**
      * Helper method
      * Used in JSP page for JSTL iteration
      * @return contacts count
      */
     public int getRecordsCount() {
         return getRecords().size();
     }


	
}