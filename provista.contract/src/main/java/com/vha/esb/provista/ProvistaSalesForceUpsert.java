package com.vha.esb.provista;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.VisitorSupport;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.model.ProvistaContractBean;
import com.vha.esb.provista.model.ProvistaSalesForceInfo;
import com.vha.esb.provista.model.UpsertResponseBean;
import com.vha.esb.provista.util.NameSpaceCleaner;

public class ProvistaSalesForceUpsert {
	
	private static Logger LOG = LoggerFactory.getLogger(ProvistaSalesForceUpsert.class);
	
	private ProvistaSalesForceInfo provistaSalesForceInfo;
	
	public ProvistaSalesForceInfo getProvistaSalesForceInfo() {
		return provistaSalesForceInfo;
	}

	public void setProvistaSalesForceInfo(
			ProvistaSalesForceInfo provistaSalesForceInfo) {
		this.provistaSalesForceInfo = provistaSalesForceInfo;
	}


	public SOAPMessage login (SOAPMessage request) {
		
		SOAPMessage loginResponse = null;
		try {
			SOAPConnectionFactory scFactory = SOAPConnectionFactory.newInstance();
			
			SOAPConnection sConn = scFactory.createConnection();
			
			loginResponse = sConn.call(request, provistaSalesForceInfo.getSFEndPoint());		
		
		} catch (UnsupportedOperationException e1) {
			e1.printStackTrace();
		
		} catch (SOAPException e1) {
			e1.printStackTrace();
		}
		
		
		return loginResponse;
	}
	
	
	public String getSessionID (SOAPMessage response) throws SOAPException, IOException, DocumentException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		String strMsg = new String(out.toByteArray());
		
		LOG.info(strMsg);
		
		SAXReader sReader = new SAXReader();
		
		Document document = sReader.read(new StringReader(strMsg));
		
		document.accept(new NameSpaceCleaner());
		
		XPath xpath = document.createXPath("/Envelope/Body/loginResponse/result/sessionId");
		Node sessionNode = xpath.selectSingleNode(document);
		
		String sessionID = null;
		
		if (sessionNode != null) {
			sessionID = sessionNode.getText();
		}
		LOG.info("session ID: " + sessionID);
		
		return sessionID;
	}
	
	public String soapMessageToString(SOAPMessage message) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			message.writeTo(out);
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(out.toByteArray());
	
	}
	
	public String getMetadataServerUrl (SOAPMessage response) throws SOAPException, IOException, DocumentException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		String strMsg = new String(out.toByteArray());
		
		LOG.info(strMsg);
		
		SAXReader sReader = new SAXReader();
		
		Document document = sReader.read(new StringReader(strMsg));
		
		document.accept(new NameSpaceCleaner());
		
		XPath xpath = document.createXPath("/Envelope/Body/loginResponse/result/metadataServerUrl");
		Node sessionNode = xpath.selectSingleNode(document);
		
		String metadataServerUrl = null;
		
		if (sessionNode != null) {
			metadataServerUrl = sessionNode.getText();
		}
		LOG.info("metadataServerUrl: " + metadataServerUrl);
		
		return metadataServerUrl;
	}	
	
	
	public SOAPMessage upsert(SOAPMessage request){
		
		SOAPMessage upsertResponse = null;
		try {
			SOAPConnectionFactory scFactory = SOAPConnectionFactory.newInstance();
			
			SOAPConnection sConn = scFactory.createConnection();

			upsertResponse = sConn.call(request, provistaSalesForceInfo.getMetadataServerUrl());		
		
		} catch (UnsupportedOperationException e1) {
			
			LOG.error("UnsupportedOperationException: ", e1);
		
		} catch (SOAPException e1) {

			LOG.error("SOAPExcepiton: ", e1);
		}
		
		
		return upsertResponse;	
	}
	public SOAPMessage createUpsertRequest(String sessionID, ProvistaContractBean provistContract) throws SOAPException {
		
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = soapPart.getEnvelope();
        
//        envelope.addNamespaceDeclaration("urn",  "urn:enterprise.soap.sforce.com");
//        envelope.addNamespaceDeclaration("xsi",  "http://www.w3.org/2001/XMLSchema-instance");
        
        envelope.addNamespaceDeclaration("urn",  provistaSalesForceInfo.getUrnNameSpace());
        envelope.addNamespaceDeclaration("xsi",  provistaSalesForceInfo.getXsiNameSpace());

        SOAPHeader soapHeader = envelope.getHeader();
        SOAPElement soapHeaderElem = soapHeader.addChildElement("SessionHeader", "urn");
        SOAPElement soapHeaderElemChild = soapHeaderElem.addChildElement("sessionId", "urn");
        soapHeaderElemChild.addTextNode(sessionID);
        
        
        SOAPBody soapBody = envelope.getBody();
        
        SOAPElement upsertElem = soapBody.addChildElement("upsert", "urn");
        SOAPElement xidElem = upsertElem.addChildElement("externalIDFieldName", "urn");
        xidElem.addTextNode("Contract_Number__c");
        
        SOAPElement sObjectElem = upsertElem.addChildElement("sObjects", "urn");
        sObjectElem.addAttribute(new QName("xsi:type"), "urn:Contract");
        
        SOAPElement contractNumber = sObjectElem.addChildElement("Contract_Number__c", "urn");
        contractNumber.addTextNode(provistContract.getContractNumber());

        SOAPElement contractName = sObjectElem.addChildElement("Contract_Name__c", "urn");
        contractName.addTextNode(provistContract.getContractName());
        
        if(provistContract.getProductSpendCategory() != null) {
        	SOAPElement psCategory = sObjectElem.addChildElement("Product_Spend_Category__c", "urn");
        	psCategory.addTextNode(provistContract.getProductSpendCategory());
        }
        
        SOAPElement psStatus = sObjectElem.addChildElement("Emptoris_Status__c", "urn");
        psStatus.addTextNode(provistContract.getStatus());

        if(provistContract.getContractStartDate() != null){
        	SOAPElement startDate = sObjectElem.addChildElement("StartDate", "urn");
        	startDate.addTextNode(provistContract.getContractStartDate());
        }
        if(provistContract.getContractEndDate() != null) {
        	SOAPElement endDate = sObjectElem.addChildElement("EndDate", "urn");
        	endDate.addTextNode(provistContract.getContractEndDate());
        }
        
        if(provistContract.getFormRequired() != null) {
        	SOAPElement formRequired = sObjectElem.addChildElement("Form_Required__c", "urn");
        	formRequired.addTextNode(provistContract.getFormRequired());
        }
        
        if(provistContract.getDistributionMethod() != null) {
        	SOAPElement distMethod = sObjectElem.addChildElement("Distribution_Method__c", "urn");
        	distMethod.addTextNode(provistContract.getDistributionMethod());
        }
        
        if(provistContract.getRevenueFeePct() != null) {
        	SOAPElement revenueFeePct = sObjectElem.addChildElement("Revenue_Fee_Percent__c", "urn");
        	revenueFeePct.addTextNode(provistContract.getRevenueFeePct());
        }
        
        if(provistContract.getRevenueFeeDetail() != null) {
        	SOAPElement revenueFeeDetail = sObjectElem.addChildElement("Revenue_Fee_Detail__c", "urn");
        	revenueFeeDetail.addTextNode(provistContract.getRevenueFeeDetail());
        }
        
        if(provistContract.getProductCategory() != null){
        	SOAPElement pCategoryR = sObjectElem.addChildElement("Product_Category__r", "urn");
        	SOAPElement pCategoryRName = pCategoryR.addChildElement("Name", "urn");
        	pCategoryRName.addTextNode(provistContract.getProductCategory());
        }
        
        if(provistContract.getVendorId() != null) {
        	SOAPElement account = sObjectElem.addChildElement("Account", "urn");
        	SOAPElement vendorID = account.addChildElement("Vendor_ID__c", "urn");
        	vendorID.addTextNode(provistContract.getVendorId());
        }
        soapMessage.saveChanges();

        printMessage("Requeset: ", soapMessage);
		
		return soapMessage;
	}		
	
	
	public  SOAPMessage createLoginRequest() throws SOAPException {
		
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "urn:enterprise.soap.sforce.com";

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("urn", serverURI);

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("login", "urn");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("username", "urn");
        soapBodyElem1.addTextNode(this.provistaSalesForceInfo.getSFUserName());
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("password", "urn");
        soapBodyElem2.addTextNode(this.provistaSalesForceInfo.getSFPassWord());
        soapMessage.saveChanges();

        printMessage("Requeset: ", soapMessage);
        
        return soapMessage;
    }	

	public static void printMessage (String type, SOAPMessage message) {

		LOG.info(type + "SOAP Message = ");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			message.writeTo(out);
			String msgString = new String(out.toByteArray());
			LOG.info(msgString);
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			LOG.error("SOAP Excepiton: ", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error("IO Excepiton: ", e);
		}
        
        LOG.info("\n");
	}

	private static ProvistaSalesForceInfo initProvistaSF() {
		String SFEndPoint = "https://test.salesforce.com/services/Soap/c/26.0/0DFU000000019ks";
		String metadataServerUrl = "https://cs10.salesforce.com/services/Soap/c/26.0/00DJ0000002lyZo/0DFU000000019ks";
		String urnNameSpace  = "urn:enterprise.soap.sforce.com";
		String xsiNameSpace  = "http://www.w3.org/2001/XMLSchema-instance";	
		String SFUserName = "integration-esb@provistaco.com.full";
		String SFPassWord = "ProvistaESB13";
		
		ProvistaSalesForceInfo psfInfo = new ProvistaSalesForceInfo();
		psfInfo.setSFUserName(SFUserName);
		psfInfo.setSFPassWord(SFPassWord);
		psfInfo.setSFEndPoint(SFEndPoint);
		psfInfo.setMetadataServerUrl(metadataServerUrl);
		psfInfo.setUrnNameSpace(urnNameSpace);
		psfInfo.setXsiNameSpace(xsiNameSpace);
		return psfInfo;

	}
	
	public UpsertResponseBean doUpsert(String xmlData) throws SOAPException, DocumentException, IOException {

		//Parse xml data
		ProvistaContractBean pcBean = new AgreementsXmlParser().parseXML(xmlData);
		pcBean.show();
		
		UpsertResponseBean upsertResult  = null;
		
		if (pcBean == null || pcBean.isSkip()) {
			upsertResult = new UpsertResponseBean();
			upsertResult.setSkipped(true);
			upsertResult.setSendEmail(false);
			upsertResult.setProvistaContractInfo(pcBean);
			return upsertResult;
		} 

		//---------------------------LOGIN----------------------------------------
		SOAPMessage request = createLoginRequest();
		
		SOAPMessage response = login(request);
		
		printMessage("Response: ", response);
		
		String sessionID = null;
		
		String metadataServierURL = null;
		
		try {
			sessionID = getSessionID(response);
			metadataServierURL = getMetadataServerUrl(response);
			
		} catch (IOException e) {
			LOG.error("IOException: ", e);
			
		} catch (DocumentException e) {
			LOG.error("DocumentException: ", e);
			
		}
		
		LOG.info("main: session ID: " + sessionID);
		LOG.info("main: Metadata Server URL: " + metadataServierURL);

		pcBean.show();

		//---------------------------UPSERT----------------------------------------
		SOAPMessage upsertRequest = createUpsertRequest(sessionID, pcBean);

		printMessage("upsert Request: ", upsertRequest);

		SOAPMessage upsertResponse = upsert(upsertRequest);

		printMessage("Provista Upsert Response" , upsertResponse);

		upsertResult = ParseUpsertResponse.parseUpsertResponse(upsertResponse);
		
		if (upsertResult != null) {
			upsertResult.setProvistaContractInfo(pcBean);
			upsertResult.show();
		}
		
		return upsertResult;
	}
	
	
	public static void main(String[] args) throws SOAPException, DocumentException, IOException {
		
		ProvistaSalesForceUpsert psfUpsert = new ProvistaSalesForceUpsert();
		
		ProvistaSalesForceInfo psfInfo = initProvistaSF();
		
		psfUpsert.setProvistaSalesForceInfo(psfInfo);

		String xmlData = AgreementsXmlParser.getStringContentFromFile();

		UpsertResponseBean upsertResult = psfUpsert.doUpsert(xmlData);
		upsertResult.show();
		
	}
	

}
