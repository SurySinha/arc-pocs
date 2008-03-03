package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: olegk
 * Date: 29.02.2008
 * Time: 15:29:10
 */
public class ContactsServiceResponse implements IsSerializable {
    public String methodName;
    public String typeResult;

  /**
   * This field is a List that must always contain Strings.
   *
   * @gwt.typeArgs <java.lang.String>
   */
    public List result = new Vector();


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result.add(result);
    }


    public String getTypeResult() {
        return typeResult;
    }

    public void setTypeResult(String typeResult) {
        this.typeResult = typeResult;
    }

    public ContactsServiceResponse() {
    }
}
