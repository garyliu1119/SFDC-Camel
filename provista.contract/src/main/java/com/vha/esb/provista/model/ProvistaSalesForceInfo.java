package com.vha.esb.provista.model;

public class ProvistaSalesForceInfo {
	private String SFEndPoint;        // = "https://test.salesforce.com/services/Soap/c/26.0/0DFU000000019ks";
	private String metadataServerUrl; // = "https://cs10.salesforce.com/services/Soap/c/26.0/00DJ0000002lyZo/0DFU000000019ks";
	private String urnNameSpace;      // = "urn:enterprise.soap.sforce.com";
	private String xsiNameSpace;      // = "http://www.w3.org/2001/XMLSchema-instance";	
	private String SFUserName;        // = "integration-esb@provistaco.com.full";
	private String SFPassWord;        // = "ProvistaESB13";
	
	
	public String getSFEndPoint() {
		return SFEndPoint;
	}
	public void setSFEndPoint(String sFEndPoint) {
		SFEndPoint = sFEndPoint;
	}
	public String getMetadataServerUrl() {
		return metadataServerUrl;
	}
	public void setMetadataServerUrl(String metadataServerUrl) {
		this.metadataServerUrl = metadataServerUrl;
	}
	public String getUrnNameSpace() {
		return urnNameSpace;
	}
	public void setUrnNameSpace(String urnNameSpace) {
		this.urnNameSpace = urnNameSpace;
	}
	public String getXsiNameSpace() {
		return xsiNameSpace;
	}
	public void setXsiNameSpace(String xsiNameSpace) {
		this.xsiNameSpace = xsiNameSpace;
	}
	public String getSFUserName() {
		return SFUserName;
	}
	public void setSFUserName(String sFUserName) {
		SFUserName = sFUserName;
	}
	public String getSFPassWord() {
		return SFPassWord;
	}
	public void setSFPassWord(String sFPassWord) {
		SFPassWord = sFPassWord;
	}

	
}
