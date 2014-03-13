package com.vha.esb.provista;

import java.util.ArrayList;
import java.util.List;

public class LoginInfo {
	public List<String> getLoginInfo(){
		List<String> loginInfo = new ArrayList<String>();
		loginInfo.add("integration-esb@provistaco.com.full");
		loginInfo.add("ProvistaESB13");
		return loginInfo;
	}

}
