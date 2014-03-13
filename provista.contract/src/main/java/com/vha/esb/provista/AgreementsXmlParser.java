package com.vha.esb.provista;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vha.esb.provista.model.ProvistaContractBean;

public class AgreementsXmlParser {
	
	private static Logger LOG = LoggerFactory.getLogger(AgreementsXmlParser.class);
	
	public static String getStringContentFromFile(){
        File f = new File("C:/provista-contract/provista.contract/src/main/resources/Agreements.xml");
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
	}
	
	public ProvistaContractBean parseXML(String sourceData) throws DocumentException{
		
		Document doc = DocumentHelper.parseText(sourceData);
		
		ProvistaContractBean pcBean = new ProvistaContractBean();
		
		//emcID
		org.dom4j.Node nodeECMId = doc.selectSingleNode( "//tns:Agreement/tns:id");
		if(nodeECMId!=null){
			String ecmID = nodeECMId.getStringValue();
			pcBean.setEcmID(ecmID);
			LOG.info("ecmID=" + ecmID);

		}
		
		//buying company ID
//		org.dom4j.Node nodeBuyerID = doc.selectSingleNode( "tns:Agreement/tns:partyRole[tns:roleName='Buying Company']/tns:Party/tns:id");
		org.dom4j.Node nodeBuyerID = doc.selectSingleNode( "//tns:Agreement/tns:partyRole[tns:roleName='Buying Company']/tns:Party[tns:name='Provista']/tns:id");
		String buyerID = null;
		if(nodeBuyerID!=null ){
			buyerID = nodeBuyerID.getStringValue();
			pcBean.setBuyerID(buyerID);
			LOG.info("buyerID=" + buyerID);
		} 
		
		if(buyerID != null && (buyerID.equals("019") || buyerID.equals("055"))){
			LOG.info("buyerID=" + buyerID);
		} else {
			pcBean.setSkip(true);
		}

		
		//contract status
		Node nodeStatus = doc.selectSingleNode( "//tns:Agreement/tns:status");
		if(nodeStatus!=null){
			String contractStatus = nodeStatus.getStringValue();
			pcBean.setStatus(contractStatus);
			LOG.info("contractStatus=" + contractStatus);
		}
		
		org.dom4j.Node nodeContractClass = doc.selectSingleNode( "//tns:Agreement/tns:term[tns:name='contract_class']/tns:value");
		String contractClass = null;
		if(nodeContractClass!=null){
			contractClass = nodeContractClass.getStringValue();
			contractClass = (contractClass== null ? "" : contractClass);
			pcBean.setContractClass(contractClass);
			LOG.info("contractClass=" + contractClass);
		}
		
		if(contractClass.trim().equals("Supplier Agreement") || contractClass.trim().equals("Distribution Agreement")){
			//pcBean.setSkip(false);
		} else {
			pcBean.setSkip(true);
		}

		//contract number
		Node nodeNumber = doc.selectSingleNode( "//tns:Agreement/tns:number");
		if(nodeNumber!=null){
			String contractNumber = nodeNumber.getStringValue();
			pcBean.setContractNumber(contractNumber);
			LOG.info("contractNumber=" + contractNumber);
		}
		
		//contract name
		Node nodeName = doc.selectSingleNode( "//tns:Agreement/tns:name");
		if(nodeName!=null){
			String contractName = nodeName.getStringValue();
			pcBean.setContractName(contractName);
			LOG.info("contractName=" + contractName);
		}

		//distribution method
		org.dom4j.Node nodeDistrType = doc.selectSingleNode( "//tns:Agreement/tns:term[tns:name='distribution_method']/tns:value");
		if(nodeDistrType!=null) {
			String distributionMethod = nodeDistrType.getStringValue();
			pcBean.setDistributionMethod(distributionMethod);
			LOG.info("distribution method=" + distributionMethod);
		}

		//end date
		org.dom4j.Node nodeEndDate = doc.selectSingleNode( "//tns:Agreement/tns:effectiveEndDate");
		if(nodeEndDate!=null) {
			String endDate = nodeEndDate.getStringValue();
				pcBean.setContractEndDate(endDate);
				LOG.info("endDate=" + endDate);
		}
		
		//start date
		org.dom4j.Node nodeStartDate = doc.selectSingleNode( "//tns:Agreement/tns:effectiveStartDate");
		if(nodeStartDate!=null) {
			String startDate = nodeStartDate.getStringValue();
			pcBean.setContractStartDate(startDate);
			LOG.info("startDate=" + startDate);
		}

		//commit required
		org.dom4j.Node nodeCommitReq = doc.selectSingleNode( "//tns:Agreement/tns:term[tns:name='commitment_required']/tns:value");
		if(nodeCommitReq!=null) {
			String commitRequired = nodeCommitReq.getStringValue();
			pcBean.setFormRequired(commitRequired);
			LOG.info("commitRequired=" + commitRequired);
		}

		//product category
		org.dom4j.Node nodeProdSegKey = doc.selectSingleNode( "//tns:Agreement/tns:productClass[tns:type='product_segment_key']/tns:name");
		if(nodeProdSegKey!=null) {
			String productCategory = nodeProdSegKey.getStringValue();
			pcBean.setProductCategory(productCategory);
			LOG.info("productCategory=" + productCategory);
		}

		//product category
		org.dom4j.Node nodeProdSpecndCategory = doc.selectSingleNode( "tns:Agreement/tns:term[tns:name='product_sub_category']/tns:value");
		if(nodeProdSpecndCategory!=null) {
			String productSpendCategory = nodeProdSpecndCategory.getStringValue();
			pcBean.setProductSpendCategory(productSpendCategory);
			LOG.info("productSpendCategory=" + productSpendCategory);
		}

		//revenue fee detail
		org.dom4j.Node nodeRevenueFeeDetail = doc.selectSingleNode( "tns:Agreement/tns:term[tns:name='revenue_fee_detail']/tns:value");
		if(nodeRevenueFeeDetail!=null){
			String revenueFeeDetail = nodeRevenueFeeDetail.getStringValue();
			pcBean.setRevenueFeeDetail(revenueFeeDetail);
			LOG.info("revenueFeeDetail=" + revenueFeeDetail);
		}
		
		//revenue percent
		org.dom4j.Node nodeRevenuePct = doc.selectSingleNode( "tns:Agreement/tns:term[tns:name='revenue_pct']/tns:value");
		if(nodeRevenuePct!=null){
			String revenuePct = nodeRevenuePct.getStringValue();
			pcBean.setRevenueFeePct(revenuePct);
			LOG.info("revenuePct=" + revenuePct);
		}
		
		//vendor ID
		org.dom4j.Node nodeVendorID = doc.selectSingleNode( "tns:Agreement/tns:partyRole[tns:roleName='Supplier']/tns:Party/tns:id");
		if(nodeVendorID!=null){
			String vendorID = nodeVendorID.getStringValue();
			pcBean.setVendorId(vendorID);
			LOG.info("vendorID=" + vendorID);
		}

		//vendor name
		org.dom4j.Node nodeVendorName = doc.selectSingleNode( "tns:Agreement/tns:partyRole[tns:roleName='Supplier']/tns:Party/tns:name");
		if(nodeVendorName!=null){
			String vendorName = nodeVendorName.getStringValue();
			pcBean.setVendorName(vendorName);
			LOG.info("vendorName=" + vendorName);
		}

		//pcBean.show();
		
		return pcBean;
	}
	
	public ProvistaContractBean process(){
		
		ProvistaContractBean pcBean = new ProvistaContractBean();
		pcBean.setContractName("OfficeMax Office Products");
		pcBean.setContractNumber("BP0011");
		pcBean.setProductCategory("Acute Care Beds");
		pcBean.setContractStartDate("2012-12-15");
		pcBean.setContractEndDate("2014-08-31");
		pcBean.setFormRequired("N");
		pcBean.setDistributionMethod("Direct");
		pcBean.setRevenueFeeDetail("5.3%");
		pcBean.setRevenueFeePct("0");
		pcBean.setProductCategory("Medical Products");
		
		return pcBean;
	}
	
	public static void main(String[] args){
		
		String xmlData = getStringContentFromFile();
		//LOG.info(xmlData);
		
		AgreementsXmlParser xmlParser = new AgreementsXmlParser();
		
		try {
			ProvistaContractBean pcBean = xmlParser.parseXML(xmlData);
			pcBean.show();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
