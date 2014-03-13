package com.vha.esb.provista;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.model.ProcessInfo;
import com.vha.esb.provista.model.UpsertResponseBean;
import com.vha.esb.provista.util.EmailHandler;

public class ContractMessageProcessor implements Processor {
	
	private static Logger log = LoggerFactory.getLogger(ContractMessageProcessor.class);

	private ProvistaSalesForceUpsert provistaSFDCUpsert;
	
	private ProcessInfo provistaProcessInfo;
	
	private EmailHandler emailHandler;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		log.info((String)exchange.getIn().getHeader("type") + new Date());
		String xmlData = exchange.getIn().getBody().toString();
		log.info(xmlData);
		
		UpsertResponseBean upsertResult = provistaSFDCUpsert.doUpsert(xmlData);
		//exchange.getOut().setBody(xmlData);
		setOutBoundInfo(exchange, upsertResult);
		
		if (upsertResult.isSendEmail()) {
			emailHandler.sendEmail(upsertResult);
		}
		
	}
	
	
	public EmailHandler getEmailHandler() {
		return emailHandler;
	}


	public void setEmailHandler(EmailHandler emailHandler) {
		this.emailHandler = emailHandler;
	}


	public ProcessInfo getProvistaProcessInfo() {
		return provistaProcessInfo;
	}

	public void setProvistaProcessInfo(ProcessInfo provistaProcessInfo) {
		this.provistaProcessInfo = provistaProcessInfo;
	}

	public ProvistaSalesForceUpsert getProvistaSFDCUpsert() {
		return provistaSFDCUpsert;
	}

	public void setProvistaSFDCUpsert(ProvistaSalesForceUpsert provistaSFDCUpsert) {
		this.provistaSFDCUpsert = provistaSFDCUpsert;
	}

	private void setOutBoundInfo(Exchange exchange, UpsertResponseBean responseBean){
	 
		try {
			exchange.getOut().setHeader("EventID",
					exchange.getIn().getHeader("EventID").toString());
		} catch (Exception ex) {
			exchange.getOut().setHeader("EventID", "");
		}
		
		try {
			exchange.getOut().setHeader("ReferenceID",
					exchange.getIn().getHeader("EventID").toString());
		} catch (Exception ex) {
			exchange.getOut().setHeader("ReferenceID", "");
		}
		
		String updateMessageStatus = "";	
		String updateMessageType = "Log";

		if(responseBean.isSkipped()) {
			updateMessageStatus = "Message Skipped";
			updateMessageType = "Log";
			
		} else if(responseBean.isSuccess()) {
			updateMessageStatus = "Provista System Updated";
			updateMessageType = "Log";
		
		} else {
			updateMessageStatus = "Error Message"; 
			updateMessageType = "Error";
			
			exchange.getOut().setHeader("Error_Message", responseBean.getSoapMessage());

		}
		
		exchange.getOut().setHeader("Message", updateMessageStatus);
		exchange.getOut().setHeader("MessageType", updateMessageType);
		
		log.info("Message Status: " + updateMessageStatus);
		log.info("Message Type: " + updateMessageType);
		
		exchange.getOut().setHeader("ReferenceID", provistaProcessInfo.getProcessName());
		exchange.getOut().setHeader("EventID", provistaProcessInfo.getProcessName());

		exchange.getOut().setHeader("ProcessSequenceNumber", provistaProcessInfo.getProcessSequenceNumber());
		exchange.getOut().setHeader("StepNumber",   provistaProcessInfo.getStepNumber());
		exchange.getOut().setHeader("Process_Name", provistaProcessInfo.getProcessName());
		exchange.getOut().setHeader("Project_Name", provistaProcessInfo.getProject());
		exchange.getOut().setHeader("Attempt",      provistaProcessInfo.getAttempt());
		exchange.getOut().setHeader("Bid1_Name",    provistaProcessInfo.getBid1_Name());
		exchange.getOut().setHeader("Bid2_Name",    provistaProcessInfo.getBid2_Name());
		exchange.getOut().setHeader("Bid3_Name",    provistaProcessInfo.getBid3_Name());
		exchange.getOut().setHeader("Bid4_Name",    provistaProcessInfo.getBid4_Name());
		if(!responseBean.isSkipped()){
			exchange.getOut().setHeader("Bid1_Value",   responseBean.getProvistaContractInfo().getContractName());
			exchange.getOut().setHeader("Bid2_Value",   responseBean.getProvistaContractInfo().getContractNumber());
			exchange.getOut().setHeader("Bid3_Value",   responseBean.getProvistaContractInfo().getVendorName());
			exchange.getOut().setHeader("Bid4_Value",   responseBean.getProvistaContractInfo().getEcmID());
		}
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		
		Date date = new Date();
		exchange.getOut().setHeader("DateTime", simpleDateFormat.format(date));

		
		
	}
}
