package com.arcmind.contact.form;

import org.apache.struts.validator.ValidatorForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.arcmind.contact.model.Contact;
import com.arcmind.contact.model.ContactRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 17.01.2008
 * Time: 23:11:36
 * To change this template use File | Settings | File Templates.
 */
public class ContactForm extends ValidatorForm {

    // Fields
    private long id;
    private String firstName;
    private String lastName;
    private String persistCommand;
    private boolean rendered;
    private boolean hideAddNewLink;

    /** Contact form collaborates with contactRepository. */
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


    /**
     * Standard Struts1 validation of input parameters
     * for a form bean
     * @param mapping
     * @param request
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);

        if (errors == null) {
            errors = new ActionErrors();
        }

        if (!errors.isEmpty()){
            setRendered(true);
            setHideAddNewLink(true);
        }

        request.setAttribute("contactForm",this);
        return errors;
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
