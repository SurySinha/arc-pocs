package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by IntelliJ IDEA.
 * User: Alec
 * Date: 02.03.2008
 * Time: 1:11:14
 * To change this template use File | Settings | File Templates.
 */
public class PersonBundle implements IsSerializable {

    Person[] persons;
    String message;

    public PersonBundle() {
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
