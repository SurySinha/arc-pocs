package com.arcmind.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class CalculatorForm extends ValidatorForm {
	/*
	 * Generated fields
	 */

	/** result property */
	private int result;

	/** secondNumber property */
	private String secondNumber;

	/** firstNumber property */
	private String firstNumber;
	
	/** visible property */
	private boolean visibleResult;
	
	
	 
	/**
	 * @return the visibleResult
	 */
	public boolean isVisibleResult() {
		return visibleResult;
	}

	/**
	 * @param visibleResult the visibleResult to set
	 */
	public void setVisibleResult(boolean visibleResult) {
		this.visibleResult = visibleResult;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null) {
			errors = new ActionErrors();
		}

		if (!errors.isEmpty()){
			setVisibleResult(false);
		}

		return errors;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the result.
	 * @return int
	 */
	public int getResult() {
		return result;
	}

	/** 
	 * Set the result.
	 * @param result The result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the secondNumber
	 */
	public String getSecondNumber() {
		return secondNumber;
	}

	/**
	 * @param secondNumber the secondNumber to set
	 */
	public void setSecondNumber(String secondNumber) {
		this.secondNumber = secondNumber;
	}

	/**
	 * @return the firstNumber
	 */
	public String getFirstNumber() {
		return firstNumber;
	}

	/**
	 * @param firstNumber the firstNumber to set
	 */
	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}

	
}