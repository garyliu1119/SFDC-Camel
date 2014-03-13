package com.vha.esb.provista;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.model.UpsertResponseBean;


public class ParseUpsertResponse {
	private static Logger LOG = LoggerFactory.getLogger(ParseUpsertResponse.class);
	
	private static final String SEND_EMAIL_FILTER="not found for field Vendor_ID__c in entity Account";

	public static UpsertResponseBean parseUpsertResponse (final SOAPMessage response) throws SOAPException, IOException, DocumentException {
		UpsertResponseBean responseBean = new UpsertResponseBean();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		String strMsg = new String(out.toByteArray());
		
		responseBean.setSoapMessage(strMsg);
		
		LOG.info(strMsg);
		
		SAXReader sReader = new SAXReader();
		
		Document document = sReader.read(new StringReader(strMsg));
		
		document.accept(new com.vha.esb.provista.util.NameSpaceCleaner());
		
		XPath xpathSuccess = document.createXPath("/Envelope/Body/upsertResponse/result/success");
		Node successNode = xpathSuccess.selectSingleNode(document);
		String status = null;
		if (successNode != null) {
			status = successNode.getText();
			responseBean.setSuccess(Boolean.parseBoolean(status));
		}
		LOG.info("Status: " + status);
		
		XPath xpathCreated = document.createXPath("/Envelope/Body/upsertResponse/result/created");
		Node createdNode = xpathCreated.selectSingleNode(document);
		String created = null;
		if (createdNode != null) {
			created = createdNode.getText();
			responseBean.setCreated(Boolean.parseBoolean(created));
		}
		LOG.info("created: " + created);
		
		if(responseBean.isSuccess()) return responseBean;
		
		responseBean.setSendEmail(true);

		XPath errorMessageXpath = document.createXPath("/Envelope/Body/upsertResponse/result/errors/message");
		Node errorMessageNode = errorMessageXpath.selectSingleNode(document);
		String errorMessage = null;
		if (errorMessageNode != null) {
			errorMessage = errorMessageNode.getText();
			responseBean.setErrorMessage(errorMessage);

			if(errorMessage.contains("not found for field Vendor_ID__c in entity Account")){
				responseBean.setSendEmail(true);
			}
		}
		LOG.info("Error Message: " + errorMessage);
		
		XPath statusCodeXpath = document.createXPath("/Envelope/Body/upsertResponse/result/errors/statusCode");
		Node statusCodeNode = statusCodeXpath.selectSingleNode(document);
		String statusCode = null;
		if (statusCodeNode != null) {
			statusCode = statusCodeNode.getText();
			responseBean.setStatusCode(statusCode);
		}
		LOG.info("Status Code: " + statusCode);
		
		return responseBean;
	}
	

}
