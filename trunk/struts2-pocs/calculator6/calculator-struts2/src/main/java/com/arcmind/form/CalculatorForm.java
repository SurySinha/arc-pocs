package com.arcmind.form;


public class CalculatorForm  {
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
        this.secondNumber = secondNumber.trim();
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
        this.firstNumber = firstNumber.trim();
	}

	
}