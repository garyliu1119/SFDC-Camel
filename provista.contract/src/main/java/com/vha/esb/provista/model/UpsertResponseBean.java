package com.vha.esb.provista.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.ParseUpsertResponse;

public class UpsertResponseBean {
	private static Logger LOG = LoggerFactory.getLogger(UpsertResponseBean.class);
	
	private boolean created;
	private String errorMessage;
	private ProvistaContractBean provistaContractInfo;
	private boolean sendEmail;
	private boolean skipped;
	private String soapMessage;
	private String statusCode;
	private boolean success;
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public ProvistaContractBean getProvistaContractInfo() {
		return provistaContractInfo;
	}
	public String getSoapMessage() {
		return soapMessage;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public boolean isCreated() {
		return created;
	}
	public boolean isSendEmail() {
		return sendEmail;
	}
	public boolean isSkipped() {
		return skipped;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setProvistaContractInfo(ProvistaContractBean provistaContractInfo) {
		this.provistaContractInfo = provistaContractInfo;
	}
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}
	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
	}
	public void setSoapMessage(String soapMessage) {
		this.soapMessage = soapMessage;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void show(){

		LOG.info("skipped=" + skipped);
		LOG.info("sendEmail=" + sendEmail);
		LOG.info("success=" + success);
		LOG.info("created=" + created);
		LOG.info("messaage=" + errorMessage);
		LOG.info("statusCode=" + statusCode);
		LOG.info("SOAP Message=" + soapMessage);
		if(provistaContractInfo != null) {
			this.provistaContractInfo.show();
		}
	}
}
