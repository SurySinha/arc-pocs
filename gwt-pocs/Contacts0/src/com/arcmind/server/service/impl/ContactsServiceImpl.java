package com.arcmind.server.service.impl;


import com.arcmind.client.server.ContactsService;
import com.arcmind.client.server.ContactsServiceResponse;
import com.arcmind.client.server.Person;
import com.arcmind.client.server.PersonBundle;
import com.arcmind.client.model.ContactRepository;
import com.arcmind.client.model.Contact;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:40:39
 */
public class ContactsServiceImpl extends RemoteServiceServlet implements ContactsService {

    private static ContactRepository contactRepository = new ContactRepository();
    private static boolean showAddNewForm = false;
    private static Vector messages = new Vector();

    public ContactsServiceResponse showAddNewForm() {
        this.showAddNewForm = true;
        ContactsServiceResponse result = new ContactsServiceResponse();
        result.setMethodName("showAddNewForm");
        result.setTypeResult("boolean");
        result.setResult(String.valueOf(this.showAddNewForm));
        return result;
    }

    public ContactsServiceResponse isAddNewFormVisible() {
        ContactsServiceResponse result = new ContactsServiceResponse();
        result.setMethodName("isAddNewFormVisible");
        result.setTypeResult("boolean");
        result.setResult(String.valueOf(this.showAddNewForm));
        return result;
    }

    //* @gwt.typeArgs <java.lang.String>
    public ContactsServiceResponse add(String firstName, String lastName) {
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contactRepository.persist(contact);
        ContactsServiceResponse result = new ContactsServiceResponse();
        result.setMethodName("add");
        result.setTypeResult("string");
        result.setResult(contact.toString());

        //
        messages.clear();
        messages.add("Added "+contact.toString());

        showAddNewForm = false;

        return result;
    }

    public ContactsServiceResponse update(long id, String firstName, String lastName) {
        ContactsServiceResponse result = new ContactsServiceResponse();
        List contacts = contactRepository.getContacts();
        Iterator iter = contacts.iterator();
        while(iter.hasNext()){
            Contact contact = (Contact)(iter.next());
            if (contact.getId()==id){
                contact.setFirstName(firstName);
                contact.setLastName(lastName);
                messages.clear();
                messages.add("Updated "+contact.toString());
                result.setMethodName("update");
                result.setTypeResult("void");
                result.setResult(null);
                break;
            }
        }

        //
        showAddNewForm = false;

        return result;
    }

    public PersonBundle getContacts() {
        List contacts = contactRepository.getContacts();
        Person[] persons = new Person[contacts.size()];
        Iterator iter = contacts.iterator();
        int i=0;
        while(iter.hasNext()){
            Contact c = (Contact)(iter.next());
            persons[i] = new Person();
            persons[i].setFirstName(c.getFirstName());
            persons[i].setLastName(c.getLastName());
            persons[i].setId(c.getId());
            i++;
        }

        Arrays.sort(persons);
        PersonBundle personBundle = new PersonBundle();
        personBundle.setPersons(persons);

        //
        if (messages.size()>0){
            personBundle.setMessage((String)messages.get(0));
            messages.clear();
        }

        return personBundle;
    }

    public void remove(long id) {
        ContactsServiceResponse result = new ContactsServiceResponse();
        List contacts = contactRepository.getContacts();
        Iterator iter = contacts.iterator();
        while(iter.hasNext()){
            Contact contact = (Contact)(iter.next());
            if (contact.getId()==id){
                messages.clear();
                messages.add("Deleted "+contact.toString());
                contactRepository.remove(contact);
                result.setMethodName("remove");
                result.setTypeResult("void");
                result.setResult(null);
                break;
            }
        }

        //
        showAddNewForm = false;
    }

    public PersonBundle edit(long id) {
        PersonBundle personBundle = new PersonBundle();
        List contacts = contactRepository.getContacts();
        Iterator iter = contacts.iterator();
        while(iter.hasNext()){
            Contact contact = (Contact)(iter.next());
            if (contact.getId() == id){
                Person[] persons = new Person[1];
                persons[0] = new Person();
                persons[0].setId(contact.getId());
                persons[0].setFirstName(contact.getFirstName());
                persons[0].setLastName(contact.getLastName());
                personBundle.setPersons(persons);
                messages.clear();
                personBundle.setMessage("Read "+contact.toString());
                break;
            }
        }
        showAddNewForm = true;
        return personBundle;
    }

}
