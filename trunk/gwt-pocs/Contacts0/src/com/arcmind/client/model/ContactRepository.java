package com.arcmind.client.model;

import java.util.*;

public class ContactRepository {
    private Map contacts = new HashMap();
    private static long counter = 1l;

    public List getContacts() {
        return new ArrayList(contacts.values());
    }

    public synchronized Contact persist(Contact contact) {
        if (contact.id == 0) {
            contact.id = counter++;
        }
        Long key = new Long(contact.id);
        return (Contact)contacts.put(key, contact);
    }

    public synchronized void remove(Contact contact) {
        //contacts.remove(contact.id);
        Iterator keyIter = contacts.keySet().iterator();
        while(keyIter.hasNext()){
            Long key = (Long)(keyIter.next());
            if (key.longValue() == contact.id){
                contacts.remove(key);
                break;
            }
        }
    }

}