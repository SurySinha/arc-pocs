/*
 * Created on May 22, 2004
 *
 */
package com.arcmind.contact.model;

/**
 * com.arcmind.jsfquickstart.model.PhoneNumber
 * @author Richard Hightower
 *
 */
public class PhoneNumber implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String countryCode;
	private String areaCode;
	private String prefix;
	private String number;
	private String original;
	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return Returns the prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix The prefix to set.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String toString(){
		if (countryCode.equals("1")){
			return countryCode + " " + areaCode + " " + prefix + " " + number;
		}else{
			return number;
		}
	}
	
	/**
	 * @return Returns the original.
	 */
	public String getOriginal() {
		return original;
	}
	/**
	 * @param original The original to set.
	 */
	public void setOriginal(String original) {
		this.original = original;
	}
}
