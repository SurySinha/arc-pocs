package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.RemoteService;
import com.arcmind.client.model.Contact;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:35:10
 */
public interface ContactsService extends RemoteService {

    //* @gwt.typeArgs <java.lang.String>
    ContactsServiceResponse showAddNewForm();
    //* @gwt.typeArgs <java.lang.String>
    ContactsServiceResponse isAddNewFormVisible();
    //* @gwt.typeArgs <java.lang.String>
    ContactsServiceResponse add(String firstName, String secondName);
    PersonBundle getContacts();
    void remove(long id);
    PersonBundle edit(long id);
    ContactsServiceResponse update(long id, String firstName, String secondName);


}
