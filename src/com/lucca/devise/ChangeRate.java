package com.lucca.devise;

public class ChangeRate {
	private String sInitCurrency;
	private String sChangeRate;
	private String sEndCurrency;
	
	public String getsInitCurrency() {
		return sInitCurrency;
	}
	public void setsInitCurrency(String sInitCurrency) {
		this.sInitCurrency = sInitCurrency;
	}
	public String getsChangeRate() {
		return sChangeRate;
	}
	public void setsChangeRate(String sChangeRate) {
		this.sChangeRate = sChangeRate;
	}
	public String getsEndCurrency() {
		return sEndCurrency;
	}
	public void setsEndCurrency(String sEndCurrency) {
		this.sEndCurrency = sEndCurrency;
	}
}
