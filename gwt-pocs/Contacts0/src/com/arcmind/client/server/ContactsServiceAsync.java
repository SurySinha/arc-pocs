package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 25.02.2008
 * Time: 13:56:48
 */
public interface ContactsServiceAsync {
    void showAddNewForm(AsyncCallback callback);
    void isAddNewFormVisible(AsyncCallback callback);
    void add(String firstName, String secondName, AsyncCallback callback);
    void getContacts(AsyncCallback callback);
    void remove(long id, AsyncCallback callback);
    void edit(long id, AsyncCallback callback);
    void update(long id, String firstName, String secondName, AsyncCallback callback);

}
