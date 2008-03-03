package com.arcmind.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.arcmind.client.server.*;
import com.arcmind.client.model.Contact;

import java.util.Vector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Contacts implements EntryPoint {

    static boolean formVisible;
    Hyperlink linkAddNew = new Hyperlink();
    VerticalPanel formPanel = new VerticalPanel();
    Hidden id = new Hidden();
    TextBox firstName = new TextBox();
    TextBox lastName = new TextBox();
    Button submit = new Button("Add");
    Grid grid = new Grid();

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

      // Preparing XML-RPC stuff for ajax calls
      final ContactsServiceAsync contactsService =
                  (ContactsServiceAsync) GWT.create(ContactsService.class);


      ServiceDefTarget endpoint = (ServiceDefTarget) contactsService;
      String moduleRelativeURL = GWT.getModuleBaseURL() + "contacts";
      endpoint.setServiceEntryPoint(moduleRelativeURL);

      final AsyncCallback callback = new AsyncCallback() {
            public void onSuccess(Object result) {
              // do some UI stuff to show success
                if (result instanceof ContactsServiceResponse)
                parseStringResponse(result);

                if (result instanceof PersonBundle)
                updateGrid((PersonBundle)result);
            }

            public void onFailure(Throwable caught) {
              // do some UI stuff to show failure
                saveErrors(caught);
            }
      };

      contactsService.isAddNewFormVisible(callback);
      contactsService.getContacts(callback);

      // Creating widgets
      createForm(contactsService, callback);
      createAddNewLink(contactsService, callback);
      createGrid();

      //Window.alert(String.valueOf(formVisible));
      //if (!formVisible)
      RootPanel.get("addNewLink").add(linkAddNew);
  }

    private void createAddNewLink(final ContactsServiceAsync contactsService, final AsyncCallback callback) {
        linkAddNew.setText("Add new...");
        linkAddNew.addClickListener(new ClickListener() {

            public void onClick(Widget widget) {
                contactsService.showAddNewForm(callback);
                widget.setVisible(false);
            }
        });
    }

    private void parseStringResponse(Object result){
        ContactsServiceResponse response = (ContactsServiceResponse)result;

        if (response.getMethodName().equals("isAddNewFormVisible")){
            if (response.getTypeResult().equals("boolean")){
                formVisible = Boolean.valueOf((String)(response.getResult().get(0))).booleanValue();
                this.linkAddNew.setVisible(!formVisible);
                showForm(formVisible);
            }
        }

        if (response.getMethodName().equals("showAddNewForm")){
            // nothing to do with response
            refresh();
        }

        if (response.getMethodName().equals("add")){
            // nothing to do with response
            this.linkAddNew.setVisible(true);
            showForm(false);
            refresh();
        }

        if (response.getMethodName().equals("update")){
            // nothing to do with response
            refresh();
        }

    }

    private void createForm(final ContactsServiceAsync contactsService, final AsyncCallback callback){
        HorizontalPanel h1 = new HorizontalPanel();
        h1.add(makeLabel("First name"));
        h1.add(firstName);
        HorizontalPanel h2 = new HorizontalPanel();
        h2.add(makeLabel("Second name"));
        h2.add(lastName);
        formPanel.add(h1);
        formPanel.add(h2);
        HorizontalPanel h3 = new HorizontalPanel();
        h3.add(submit);
        submit.addClickListener(new ClickListener(){

            public void onClick(Widget widget) {
                if (widget instanceof Button){
                    Button b = (Button)widget;
                    if (b.getText().equals("Add"))
                    contactsService.add(firstName.getText(),lastName.getText(),callback);
                    else
                    if (b.getText().equals("Update"))
                    contactsService.update(Long.valueOf(id.getValue()).longValue(),
                            firstName.getText(), lastName.getText(),callback);
                }
            }
        });
        formPanel.add(h3);
        formPanel.setVisible(false);
        RootPanel.get("formPlaceholder").add(formPanel);
    }

    private void createGrid(){
        this.grid = new Grid(0,2);
        this.grid.setStyleName("grid");
        this.grid.setVisible(false);
        RootPanel.get("gridPlaceholder").add(this.grid);
    }

    private void showForm(boolean show){
        formPanel.setVisible(show);
    }

    public native void refresh()/*-{ 
        $wnd.location.reload();
    }-*/;

    private void updateGrid(PersonBundle personBundle){
        if (personBundle.getPersons()!=null){
            this.grid.resizeRows(personBundle.getPersons().length);
            for (int row = 0; row < personBundle.getPersons().length; ++row) {
                final Person person = personBundle.getPersons()[row];
                this.grid.setText(row, 0, person.getLastName()+","+person.getFirstName());

                this.grid.getCellFormatter().setWidth(row, 0, "250px");


                //
                HorizontalPanel horzPanel = new HorizontalPanel();
                horzPanel.setSpacing(5);
                Hyperlink linkRemove = new Hyperlink();
                linkRemove.setText("remove");
                linkRemove.setTitle("id="+person.getId());
                Hyperlink linkEdit = new Hyperlink();
                linkEdit.setText("edit");
                linkEdit.setTitle("id="+person.getId());
                linkRemove.addClickListener(new ClickListener(){
                    public void onClick(Widget widget) {
                        final ContactsServiceAsync contactsService =
                                    (ContactsServiceAsync) GWT.create(ContactsService.class);

                        ServiceDefTarget endpoint = (ServiceDefTarget) contactsService;
                        String moduleRelativeURL = GWT.getModuleBaseURL() + "contacts";
                        endpoint.setServiceEntryPoint(moduleRelativeURL);

                        final AsyncCallback callback = new AsyncCallback() {
                              public void onSuccess(Object result) {
                                  // do some UI stuff to show success
                                  refresh();
                              }

                              public void onFailure(Throwable caught) {
                                // do some UI stuff to show failure
                                  saveErrors(caught);
                              }
                        };

                        contactsService.remove(person.getId(),callback);                    }
                });
                linkEdit.addClickListener(new ClickListener(){
                    public void onClick(Widget widget) {
                        final ContactsServiceAsync contactsService =
                                    (ContactsServiceAsync) GWT.create(ContactsService.class);

                        ServiceDefTarget endpoint = (ServiceDefTarget) contactsService;
                        String moduleRelativeURL = GWT.getModuleBaseURL() + "contacts";
                        endpoint.setServiceEntryPoint(moduleRelativeURL);

                        final AsyncCallback callback = new AsyncCallback() {
                              public void onSuccess(Object result) {
                                // do some UI stuff to show success

                                  if (result instanceof PersonBundle){
                                      PersonBundle personBundle = (PersonBundle)result;
                                      //updateGrid(personBundle);
                                      Contacts.this.linkAddNew.setVisible(false);
                                      Contacts.this.showForm(true);
                                      Contacts.this.saveMessages(personBundle.getMessage());
                                      Contacts.this.submit.setText("Update");
                                      Contacts.this.id.setValue(String.valueOf(personBundle.getPersons()[0].getId()));
                                      Contacts.this.firstName.setText(personBundle.getPersons()[0].getFirstName());
                                      Contacts.this.lastName.setText(personBundle.getPersons()[0].getLastName());
                                  }
                              }

                              public void onFailure(Throwable caught) {
                                // do some UI stuff to show failure
                                  saveErrors(caught);
                              }
                        };

                        contactsService.edit(person.getId(),callback);
                    }
                });

                horzPanel.add(linkRemove);
                horzPanel.add(linkEdit);

                this.grid.setWidget(row, 1, horzPanel);

                if ( (row %2) == 1){
                    this.grid.getRowFormatter().setStyleName(row,"even");
                } else {
                    this.grid.getRowFormatter().setStyleName(row,"odd");
                }

               }
            this.grid.setVisible(true);

            //showDelayedMessage();

            if (personBundle.getMessage()!=null){
                if (!personBundle.getMessage().equals("")){
                    saveMessages(personBundle.getMessage());
                }
            }

        }


    }

    private void saveMessages(Object result) {
        RootPanel.get("msgPlaceholder").clear();
        RootPanel.get("msgPlaceholder").add(makeMessage(result.toString()));
    }

    private void saveErrors(Throwable e) {
        RootPanel.get("msgPlaceholder").clear();
        RootPanel.get("msgPlaceholder").add(makeError("Error: "+e.getMessage()));
    }

    private HTML makeLabel(String caption) {
        HTML html = new HTML(caption);
        html.setStyleName("ks-layouts-Label");
        return html;
    }

    private HTML makeError(String caption) {
        HTML html = new HTML(caption);
        html.setStyleName("error");
        return html;
    }

    private HTML makeMessage(String caption) {
        HTML html = new HTML(caption);
        html.setStyleName("message");
        return html;
    }
}     

