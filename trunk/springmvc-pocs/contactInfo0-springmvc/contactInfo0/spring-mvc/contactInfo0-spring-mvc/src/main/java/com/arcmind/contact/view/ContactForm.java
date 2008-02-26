package com.arcmind.contact.view;

import com.arcmind.contact.model.Contact;
import com.arcmind.contact.model.ContactRepository;
import java.util.List;
import java.util.Iterator;

import org.springframework.context.support.DefaultMessageSourceResolvable;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 22.01.2008
 * Time: 22:26:36
 * To change this template use File | Settings | File Templates.
 */
public class ContactForm {

    // Fields
    private long id;
    private String firstName;
    private String lastName;
    private String persistCommand;
    private boolean rendered;
    private boolean hideAddNewLink;

    /** success message  */
    private String message;

    /** error message  */
    private String error;
    

    /** Contact view collaborates with contactRepository. */
    public static ContactRepository contactRepository = new ContactRepository();

    /** The current contact that is being edited. */
    private Contact contact = new Contact();

    /**
     * Constructor
     */
    public ContactForm() {
        super();
        setPersistCommand("Add");
    }

    // Getters/setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersistCommand() {
        return persistCommand;
    }

    public void setPersistCommand(String persistCommand) {
        this.persistCommand = persistCommand;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }    

    // Static methods

    /**
     * Wraps repository persist command
     * Used for adding of modifying specified contact
     * 
      * @param contact
     * @return result of persistance
     */
    public static synchronized Contact persist(Contact contact) {
        return contactRepository.persist(contact);
    }

    // Helper method
    protected Object[] getMessageArguments(String key) {
        return new Object[]{new DefaultMessageSourceResolvable(new String[] {key})};
    }

    // Helpers //

    /**
     * Helper method used in edit controller
     * Reads contact
     * @param id
     * @return found contact reference
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
     * Helper method used in remove controller
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
