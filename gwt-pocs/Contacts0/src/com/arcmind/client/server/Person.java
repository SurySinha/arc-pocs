/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.arcmind.client.server;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Hold relevant data for Person. This class is meant to be serialized in RPC
 * calls.
 */
public class Person implements IsSerializable , Comparable {

  private String lastName;

  private String firstName;

  private long id;

  public Person() {
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  //public abstract String getSchedule(boolean[] daysFilter);

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int compareTo(Object o) {
        Person other = (Person)o;

        if (id > other.getId())
        return 1;

        if (id < other.getId())
        return -1;

        if (id == other.getId())
        return 0;

        return 0;
    }
}
