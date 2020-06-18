package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase{
	TestBase tBase;
	String URL;
	String apiUrl;
	String finalURL;
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		tBase = new TestBase();
		URL = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		finalURL = URL+apiUrl;
	}
	
	@Test
	public void getAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		httpResponse = restClient.get(finalURL);
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
				
		//Status code assertion
		Assert.assertEquals(statusCode, RESPONSE_STATUS_200, "Status code is not 200 !!!");
		
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		JSONObject jsonObj = new JSONObject(responseString);
		System.out.println("json response : "+ jsonObj);
		
		String perPage = TestUtil.getValueByJPath("/per_page", jsonObj);
		System.out.println("per page value : " + perPage);
		Assert.assertEquals(perPage, "6", "Per page value is not as matching !!!");
		
		String lastname = TestUtil.getValueByJPath("/data[1]/last_name", jsonObj);
		System.out.println("last name value : " + lastname);
		Assert.assertEquals(lastname, "Weaver", "Last Name value is not as matching !!!");
		
		Header[] hdrArray = httpResponse.getAllHeaders();
		HashMap<String, String> hdrHM = new HashMap<String, String>();
		
		for(Header header : hdrArray) {
			hdrHM.put(header.getName(), header.getValue());
		}
		System.out.println("All headers : "+hdrHM);
	}
	
	@Test
	public void getAPITestWithHdr() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> hdrMap = new HashMap<String, String>();
		hdrMap.put("Content-Type", "application/json");
		//put all headers required one by one.
		
		httpResponse = restClient.get(finalURL, hdrMap);
		
		int statusCode = httpResponse.getStatusLine().getStatusCode();
				
		//Status code assertion
		Assert.assertEquals(statusCode, RESPONSE_STATUS_200, "Status code is not 200 !!!");
		
		String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
		JSONObject jsonObj = new JSONObject(responseString);
		System.out.println("json response : "+ jsonObj);
		
		String perPage = TestUtil.getValueByJPath("/per_page", jsonObj);
		System.out.println("per page value : " + perPage);
		Assert.assertEquals(perPage, "6", "Per page value is not as matching !!!");
		
		String lastname = TestUtil.getValueByJPath("/data[1]/last_name", jsonObj);
		System.out.println("last name value : " + lastname);
		Assert.assertEquals(lastname, "Weaver", "Last Name value is not as matching !!!");
		
		Header[] hdrArray = httpResponse.getAllHeaders();
		HashMap<String, String> hdrHM = new HashMap<String, String>();
		
		for(Header header : hdrArray) {
			hdrHM.put(header.getName(), header.getValue());
		}
		System.out.println("All headers : "+hdrHM);
	}
}
