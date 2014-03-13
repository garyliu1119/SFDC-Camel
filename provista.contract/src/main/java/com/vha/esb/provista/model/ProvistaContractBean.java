package com.vha.esb.provista.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProvistaContractBean {
	private static Logger LOG = LoggerFactory.getLogger(ProvistaContractBean.class);
	
	private String buyerID;
	private String contractClass;
	private String contractEndDate;
	private String contractName;
	private String contractNumber;
	private String contractStartDate;
	private String distributionMethod;
	private String ecmID;
	private String formRequired;
	private String productCategory;
	private String productSpendCategory;
	private String revenueFeeDetail;
	private String revenueFeePct;

	private boolean skip;
	private String status;
	private String vendorId;
	private String vendorName;
	public String getBuyerID() {
		return buyerID;
	}
	public String getContractClass() {
		return contractClass;
	}
	

	
	public String getContractEndDate() {
		return contractEndDate;
	}
	public String getContractName() {
		return contractName;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public String getDistributionMethod() {
		return distributionMethod;
	}
	public String getEcmID() {
		return ecmID;
	}
	public String getFormRequired() {
		return formRequired;
	}
	public String getProductCategory() {
		return productCategory;
	}
	
	public String getProductSpendCategory() {
		return productSpendCategory;
	}
	public String getRevenueFeeDetail() {
		return revenueFeeDetail;
	}
	
	public String getRevenueFeePct() {
		return revenueFeePct;
	}
	public String getStatus() {
		return status;
	}
	
	public String getVendorId() {
		return vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public boolean isSkip() {
		return skip;
	}
	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}

	public void setContractClass(String contractClass) {
		this.contractClass = contractClass;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public void setDistributionMethod(String distributionMethod) {
		this.distributionMethod = distributionMethod;
	}
	public void setEcmID(String ecmID) {
		this.ecmID = ecmID;
	}
	public void setFormRequired(String formRequired) {
		this.formRequired = formRequired;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public void setProductSpendCategory(String productSpendCategory) {
		this.productSpendCategory = productSpendCategory;
	}
	public void setRevenueFeeDetail(String revenueFeeDetail) {
		this.revenueFeeDetail = revenueFeeDetail;
	}
	public void setRevenueFeePct(String revenueFeePct) {
		this.revenueFeePct = revenueFeePct;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void show(){
		LOG.info("=====================BEGIN: Provista Contract Info=========================");
		LOG.info("emcID: " + this.ecmID);		
		LOG.info("Contract Name: " + this.contractName);		
		LOG.info("Contract Number: " + this.contractNumber);
		LOG.info("Disbution Method: " + this.distributionMethod);
		LOG.info("Commit Required: " + this.formRequired);
		LOG.info("Contract Start Date: " + this.contractStartDate);
		LOG.info("Contract End Date: " + this.contractEndDate);
		LOG.info("Product Category: " + this.productCategory);
		LOG.info("Product Spend Category: " + this.productSpendCategory);
		LOG.info("Contract class: " + this.contractClass);
		LOG.info("Buyor ID: " + this.buyerID);
		LOG.info("Revenue Feed Detail: " + this.revenueFeeDetail);
		LOG.info("Revenue Percentage: " + this.revenueFeePct);
		LOG.info("Vendor ID: " + this.vendorId);
		LOG.info("Vendor Name: " + this.vendorName);
		LOG.info("Status: " + this.status);
		LOG.info("Skip?: " + this.skip);
		LOG.info("=====================END: Provista Contract Info=========================");
	}
}
