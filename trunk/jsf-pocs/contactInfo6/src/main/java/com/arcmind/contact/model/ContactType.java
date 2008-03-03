package com.arcmind.contact.model;

public enum ContactType {
	BUSINESS, PERSONAL;
	
	public String toString () {
		return this.name().toLowerCase();
	}
}
