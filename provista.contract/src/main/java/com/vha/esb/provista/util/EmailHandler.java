package com.vha.esb.provista.util;

import java.net.InetAddress;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.model.UpsertResponseBean;

public class EmailHandler  {
	
	private static Logger log = LoggerFactory.getLogger(EmailHandler.class);

	private String emailRecipients;
	private String emailServer;
	private String importance;
	private String priority;
	private String senderAddress;
	private String subject;
	private String x_priority;
		

	public String getEmailRecipients() {
		return emailRecipients;
	}


	public void setEmailRecipients(String emailRecipients) {
		this.emailRecipients = emailRecipients;
	}


	public String getEmailServer() {
		return emailServer;
	}


	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}


	public String getImportance() {
		return importance;
	}


	public void setImportance(String importance) {
		this.importance = importance;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getSenderAddress() {
		return senderAddress;
	}


	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getX_priority() {
		return x_priority;
	}


	public void setX_priority(String x_priority) {
		this.x_priority = x_priority;
	}


	public void sendEmail(final UpsertResponseBean responseBean) {
		try {

			String serverName = InetAddress.getLocalHost().getHostName();
			
			String mailBody = ("Hi Team, <br/><p>Server Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"
					+ serverName + "<br/>"
					+ new java.util.Date()
					+ "<br/>"
					+ "-----------------------------------------------" + "<br/>"
					+ "Contract Name: " + responseBean.getProvistaContractInfo().getContractName() + "<br/>"
					+ "Contract Number: " + responseBean.getProvistaContractInfo().getContractNumber() + "<br/>"
					+ responseBean.getErrorMessage() + "<br/>"
					+ "<br/><br/>TALENDQ<br/>####<br/>"
					+ "CAT##Talend Integration Services (TIS)##<br/>"
					+ "URG##SEV1 Major##<br/>IMP##NORMAL##</p>" 
					+ "<br/><br/>"
					+ "Thanks,<br/>"
					+ "Talend ESB Support Team.");

			
			Properties properties = new Properties();
			
			properties.put("mail.smtp.host", this.emailServer);
			
			javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);
			
			javax.mail.internet.MimeMessage mailMsg = new javax.mail.internet.MimeMessage(session);
			
			mailMsg.setHeader("Priority", this.priority);
			
			mailMsg.setHeader("X-Priority", this.x_priority);
			
			mailMsg.setHeader("Importance", this.importance);
			
			mailMsg.setContent(mailBody, "text/html");

			javax.mail.internet.InternetAddress[] addresses = javax.mail.internet.InternetAddress.parse(this.emailRecipients, false);

			mailMsg.setRecipients(javax.mail.Message.RecipientType.TO,	addresses);

			mailMsg.setFrom(new javax.mail.internet.InternetAddress(this.senderAddress));

			mailMsg.setSubject(this.subject);

			javax.mail.Transport.send(mailMsg);

			
		} catch (Exception e) {
			
			log.error("ERROR: ", e);
		}

	}

}
