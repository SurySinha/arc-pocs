package com.arcmind.client.view;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import com.arcmind.client.server.CalculatorServiceAsync;
import com.arcmind.client.server.CalculatorService;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CalculatorView implements EntryPoint {

    private static final int ADD = 0;
    private static final int SUBTRACT = 1;
    private static final int MULTIPLY = 2;
    private static final int DIVIDE = 3;
    private static final int CLEAR = 4; 
    private static final int ABOUT = 5; 

  private static class MyDialog extends DialogBox implements ClickListener {

    public MyDialog()  {
        setText("About");
        Button closeButton = new Button("Close", this);
        HTML msg = new HTML(
                "<center>Calculator sample, written with Google Web Toolkit</center>",
                true);

        DockPanel dock = new DockPanel();
        dock.setSpacing(4);

        dock.add(closeButton, DockPanel.SOUTH);
        dock.add(msg, DockPanel.NORTH);

        dock.setCellHorizontalAlignment(closeButton, DockPanel.ALIGN_RIGHT);
        dock.setWidth("100%");
        setWidget(dock);
    }
      public void onClick(Widget sender) {
          hide();
      }
  }

  /**
   * A very simple popup that closes automatically when you click off of it.
   */
  private static class MyPopup extends PopupPanel {
    public MyPopup() {
      super(true);

      HTML contents = new HTML(
          "Not a number!");
      contents.setWidth("128px");
      setWidget(contents);

      setStyleName("ks-popups-Popup");
    }
  }     

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

      final TextBox firstNumber = new TextBox();
      final TextBox secondNumber = new TextBox();

      // Create a tab bar with three items.
      final TabBar bar = new TabBar();
      bar.addTab("Add");
      bar.addTab("Subtract");
      bar.addTab("Multiply");
      bar.addTab("Divide");
      bar.addTab("Clear");
      bar.addTab("About");
      bar.selectTab(0);



      // Hook up a tab listener to do something when the user selects a tab.
      bar.addTabListener(new TabListener() {
      public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        // Let the user know what they just did.
        //Window.alert("You clicked tab " + tabIndex);
      }

      public boolean onBeforeTabSelected(SourcesTabEvents sender,
          int tabIndex) {

          switch(tabIndex){
              case ABOUT:
                  DialogBox dlg = new MyDialog();
                  dlg.center();
              break;
              case CLEAR:
                  firstNumber.setText("");
                  secondNumber.setText("");
                  RootPanel.get("msgPlaceholder").clear();
                  VerticalPanel vpanel = (VerticalPanel)(bar.getParent());
                  ((Button)(vpanel.getWidget(5))).setEnabled(false);
              break;

          }

          return true;
      }
    });


      VerticalPanel vert = new VerticalPanel();
      vert.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
      vert.add(bar);
      HorizontalPanel h1 = new HorizontalPanel();
      h1.add(firstNumber);

      HorizontalPanel h2 = new HorizontalPanel();
      h2.add(secondNumber);

      vert.add(makeLabel("First number"));
      vert.add(h1);
      vert.add(makeLabel("Second number"));
      vert.add(h2);
      final Button performCalc = new Button("Calculate",new ClickListener() {
      public void onClick(Widget sender) {

          RootPanel.get("msgPlaceholder").clear();
          
          VerticalPanel vpanel = (VerticalPanel)(sender.getParent());
          TabBar tabBar = (TabBar)(vpanel.getWidget(0));
          int selected = tabBar.getSelectedTab();

          CalculatorServiceAsync calcService =
                  (CalculatorServiceAsync) GWT.create(CalculatorService.class);

          ServiceDefTarget endpoint = (ServiceDefTarget) calcService;
          String moduleRelativeURL = GWT.getModuleBaseURL() + "calculate";
          endpoint.setServiceEntryPoint(moduleRelativeURL);

          AsyncCallback callback = new AsyncCallback() {
            public void onSuccess(Object result) {
              // do some UI stuff to show success
                saveMessages(result);
            }

            public void onFailure(Throwable caught) {
              // do some UI stuff to show failure
                saveErrors(caught);
            }
          };

          try {
              int number1 = Integer.parseInt(firstNumber.getText());
              int number2 = Integer.parseInt(secondNumber.getText());
              switch(selected){
                  case ADD:
                      calcService.add(number1,number2, callback);
                  break;
                  case SUBTRACT:
                      calcService.subtract(number1,number2, callback);
                  break;
                  case MULTIPLY:
                      calcService.multiply(number1,number2, callback);
                  break;
                  case DIVIDE:
                      if (number2 == 0) throw new Exception("Division by zero!");
                      calcService.divide(number1,number2, callback);
                  break;



              }
          } catch(Throwable e){
              saveErrors(e);
          }


      }
    });
      performCalc.setStyleName("gwt-Button-mycalc");
      performCalc.setEnabled(false);
      vert.add(performCalc);

      firstNumber.addKeyboardListener(new KeyboardListenerAdapter() {
        public void onKeyUp(Widget sender, char keyCode, int modifiers) {
          performCalc.setEnabled(firstNumber.getText().length()>0 && secondNumber.getText().length()>0);
        }
      });

      secondNumber.addKeyboardListener(new KeyboardListenerAdapter() {
        public void onKeyUp(Widget sender, char keyCode, int modifiers) {
          performCalc.setEnabled(firstNumber.getText().length()>0 && secondNumber.getText().length()>0);
        }
      });


      firstNumber.addChangeListener(new ChangeListener() {
          public void onChange(Widget widget) {
              performCalc.setEnabled(firstNumber.getText().length()>0 && secondNumber.getText().length()>0);
              if (firstNumber.getText().length()>0){
                  try{
                      Integer.parseInt(firstNumber.getText());
                  } catch(Exception e){
                      MyPopup p = new MyPopup();
                      int left = widget.getAbsoluteLeft() + 20;
                      int top = widget.getAbsoluteTop() + 15;
                      p.setPopupPosition(left, top);
                      p.show();
                  }
              }
          }
      });

      secondNumber.addChangeListener(new ChangeListener() {
          public void onChange(Widget widget) {
              performCalc.setEnabled(firstNumber.getText().length()>0 && secondNumber.getText().length()>0);
              if (secondNumber.getText().length()>0){
                  try{
                      Integer.parseInt(secondNumber.getText());
                  } catch(Exception e){
                      MyPopup p = new MyPopup();
                      int left = widget.getAbsoluteLeft() + 20;
                      int top = widget.getAbsoluteTop() + 15;
                      p.setPopupPosition(left, top);
                      p.show();
                  }
              }
          }
      });


      RootPanel.get("calculatorPlaceholder").add(vert);
  }

    private void saveMessages(Object result) {
        RootPanel.get("msgPlaceholder").clear();
        RootPanel.get("msgPlaceholder").add(makeMessage("Result: "+result.toString()));
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
