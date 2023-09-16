package com.test.spring.controller;

import org.apache.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.spring.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.spring.Server;
import com.test.spring.exception.ResourceNotFoundException;


import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;

import java.util.Base64;
import java.util.HashMap;
import java.util.Date;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/workflow/")
public class WorkflowController {
	private Server server;
	private String processInstance;
	private String taskId;
	public WorkflowController(Server server) {
		this.server=server;
	}

	
			
	@PostMapping("/startprocess/{container}/{process}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String startProcess(@PathVariable String container,@PathVariable String process) throws  Exception {
		
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
		String plainCredentials = customerKey + ":" + customerSecret;
		String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
		String authorizationHeader = "Basic " + base64Credentials;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/processes/"+process+"/instances"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("Authorization", authorizationHeader)
                .header("Content-Type", "application/json")
                .build();
		HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
		this.processInstance=response.body();
		return response.body(); 
		
	}
	
	public String convertArray(String str,String arrayName,String fieldName) throws JSONException, ParseException {
		String jsonString = str;
		String res="[";
		JSONObject obj = new JSONObject(jsonString);
		//JSONArray arr = obj.getJSONArray("task-summary");
		JSONArray arr = obj.getJSONArray(arrayName);
		System.out.println("Nombre d'instances :"+arr.length());
		for (int i=0;i<arr.length();i++) {
			//res=res+arr.getJSONObject(i).getBigInteger("process-instance-id").toString()+" ";
			res=res+arr.getJSONObject(i).getBigInteger(fieldName).toString()+",";
		}
		res=res.substring(0,res.length()-1);
		return res+"]";
	}
	public String convertObject(String str,String objectName,String fieldName) throws JSONException, ParseException {
		String jsonString = str;
		String field="";
		JSONObject obj = new JSONObject(jsonString);
		//JSONArray arr = obj.getJSONArray("task-summary");
		//JSONArray arr = obj.getJSONArray(arrayName);
		//System.out.println("Nombre d'instances :"+arr.length());
		/*for (int i=0;i<arr.length();i++) {
			//res=res+arr.getJSONObject(i).getBigInteger("process-instance-id").toString()+" ";
			res=res+arr.getJSONObject(i).getBigInteger(fieldName).toString()+",";
		}*/
		field=obj.getBigInteger(fieldName).toString();
		return field;
	}
	public String convertObjectDate(String str,String objectName,String fieldName) throws JSONException, ParseException {
		String jsonString = str;
		String field="";
		JSONObject obj = new JSONObject(jsonString);
		//JSONArray arr = obj.getJSONArray("task-summary");
		//JSONArray arr = obj.getJSONArray(arrayName);
		//System.out.println("Nombre d'instances :"+arr.length());
		/*for (int i=0;i<arr.length();i++) {
			//res=res+arr.getJSONObject(i).getBigInteger("process-instance-id").toString()+" ";
			res=res+arr.getJSONObject(i).getBigInteger(fieldName).toString()+",";
		}*/
		field=obj.getJSONObject(fieldName).getNumber("java.util.Date").toString();
		
		
		return field;
	}
	public String convertObjectname(String str,String objectName,String fieldName) throws JSONException, ParseException {
		String jsonString = str;
		String field="";
		JSONObject obj = new JSONObject(jsonString);
		//JSONArray arr = obj.getJSONArray("task-summary");
		//JSONArray arr = obj.getJSONArray(arrayName);
		//System.out.println("Nombre d'instances :"+arr.length());
		/*for (int i=0;i<arr.length();i++) {
			//res=res+arr.getJSONObject(i).getBigInteger("process-instance-id").toString()+" ";
			res=res+arr.getJSONObject(i).getBigInteger(fieldName).toString()+",";
		}*/
		field=obj.getString(fieldName).toString();
		
		
		return field;
	}
	
	
	@GetMapping("/procinstances/{container}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String processInstances(@PathVariable String container) throws Exception{
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()			//&status=2&status=3
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/processes/instances?status=1&status=2&page=0&pageSize=100&sortOrder=true"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			//this.taskId=this.convert(response.body()).toString();
			return this.convertArray(response.body(),"process-instance","process-instance-id"); 
			//return response.body();
	}
	
	
	@GetMapping("/procintdetail/{container}/{instanceId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String processInstanceInfo(@PathVariable String container,@PathVariable String instanceId) throws Exception{
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()			//&status=2&status=3
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/processes/instances/"+instanceId+"?withVars=false"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			//this.taskId=this.convert(response.body()).toString();
			return this.convertObject(response.body(),"process-instance","process-instance-state"); 
			//return response.body();
	}
	@GetMapping("/task/{container}/{instanceId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String taskName(@PathVariable String container,@PathVariable String instanceId) throws Exception{
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()			//&status=2&status=3
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/tasks/"+instanceId+"?withInputData=false&withOutputData=false&withAssignments=false"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			//this.taskId=this.convert(response.body()).toString();
			return "\""+this.convertObjectname(response.body(),"task-instance","task-name")+"\""; 
			//return response.body();
	}
	
	@GetMapping("/procintdetaildate/{container}/{instanceId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String processInstanceInfoDate(@PathVariable String container,@PathVariable String instanceId) throws Exception{
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()			//&status=2&status=3
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/processes/instances/"+instanceId+"?withVars=false"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			//this.taskId=this.convert(response.body()).toString();
			return this.convertObjectDate(response.body(),"process-instance","start-date"); 
			//return response.body();
	}
	
	@GetMapping("/tasklist/{processinstanceid}")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getTasks(@PathVariable String processinstanceid) throws Exception {
		
		
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/queries/tasks/instances/process/"+processinstanceid+"?page=0&pageSize=10&sortOrder=true"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			//this.taskId=this.convert(response.body()).toString();
			return this.convertArray(response.body(),"task-summary","task-id"); 
			//return response.body();
		
	}
	
	@PutMapping("/starttask/{container}/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public void startTask(@PathVariable String container,@PathVariable String id) throws IOException, InterruptedException {
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/tasks/"+id+"/states/started"))
	                .PUT(HttpRequest.BodyPublishers.noBody())
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
		
	}
	
	
	@PutMapping("/completetask")
	@CrossOrigin(origins = "http://localhost:4200")
	public void completeTask(String taskId) throws IOException, InterruptedException {
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+this.server.getContainer()+"/tasks/"+taskId+"/states/completed"))
	                .PUT(HttpRequest.BodyPublishers.noBody())
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
		
		
	}
	
	@PutMapping("/completetaskvar/{container}/{id}/{var}/{val}")
	@CrossOrigin(origins = "http://localhost:4200")
	public void completeTaskWithBody(@PathVariable String container,@PathVariable String id,@PathVariable String var,@PathVariable String val) throws IOException, InterruptedException {
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+container+"/tasks/"+id+"/states/completed"))
	                .PUT(HttpRequest.BodyPublishers.ofString("{\""+var+"\":\""+val+"\"}"))
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
		
		
	}
	
	@PutMapping("/skiptask")
	@CrossOrigin(origins = "http://localhost:4200")
	public void skipTask(String taskId) throws IOException, InterruptedException {
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"+this.server.getContainer()+"/tasks/"
	                				+taskId+"/states/skipped"))
	                .PUT(HttpRequest.BodyPublishers.noBody())
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
		
	}
	@GetMapping("/getuser")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getUserInvolved() throws IOException, InterruptedException {
		final String customerKey = this.server.getUsername();
		final String customerSecret = this.server.getPassword();
			String plainCredentials = customerKey + ":" + customerSecret;
			String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
			String authorizationHeader = "Basic " + base64Credentials;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(this.server.getIp()+"/kie-server/services/rest/server/containers/"
	                				+this.server.getContainer()+"/processes/definitions/"
	                				+this.server.getprocess()+"/entities"))
	                .GET()
	                .header("Authorization", authorizationHeader)
	                .header("Content-Type", "application/json")
	                .build();
			HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());
			return response.body(); 
		
	}
	
	
	
}
