package com.test.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="workflow")
public class Process {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idWorkflow;
	@Column(name="container_id")
	private String container_id;
	@Column(name="process_id")
	private String process_id;
	@Column(name="name")
	private String name;
	
	


	public Process() {
		
	}
	
	

	public Process(String container_id, String process_id, String name, String fileName, String fileType, byte[] data, long idWorkflow) {
		super();
		this.idWorkflow=idWorkflow;
		this.container_id = container_id;
		this.process_id = process_id;
		this.name = name;
		
	}



	



	public long getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(long idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public String getContainer_id() {
		return container_id;
	}

	public void setContainer_id(String container_id) {
		this.container_id = container_id;
	}

	public String getProcess_id() {
		return process_id;
	}

	

	public void setProcess_id(String process_id) {
		this.process_id = process_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
