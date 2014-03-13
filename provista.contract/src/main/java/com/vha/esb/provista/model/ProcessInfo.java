package com.vha.esb.provista.model;

public class ProcessInfo {
	
	private String attempt;
	private String bid1_Name;
	private String bid2_Name;
	private String bid3_Name;
	private String bid4_Name;
	
	private String processName;
	private String processSequenceNumber;
	private String project;
	private String stepNumber;

	
	
	public String getAttempt() {
		return attempt;
	}
	public String getBid1_Name() {
		return bid1_Name;
	}
	public String getBid2_Name() {
		return bid2_Name;
	}
	public String getBid3_Name() {
		return bid3_Name;
	}
	public String getBid4_Name() {
		return bid4_Name;
	}
	public String getProcessName() {
		return processName;
	}
	public String getProcessSequenceNumber() {
		return processSequenceNumber;
	}
	public String getProject() {
		return project;
	}
	public String getStepNumber() {
		return stepNumber;
	}
	public void setAttempt(String attempt) {
		this.attempt = attempt;
	}
	public void setBid1_Name(String bid1_Name) {
		this.bid1_Name = bid1_Name;
	}
	public void setBid2_Name(String bid2_Name) {
		this.bid2_Name = bid2_Name;
	}
	public void setBid3_Name(String bid3_Name) {
		this.bid3_Name = bid3_Name;
	}
	public void setBid4_Name(String bid4_Name) {
		this.bid4_Name = bid4_Name;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public void setProcessSequenceNumber(String processSequenceNumber) {
		this.processSequenceNumber = processSequenceNumber;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}
	
}