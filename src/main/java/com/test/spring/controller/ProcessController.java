package com.test.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;


import com.test.spring.repository.ProcessRepository;
import com.test.spring.exception.ResourceNotFoundException;
import com.test.spring.model.Process;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/workflow/")
public class ProcessController {
	
	@Autowired
	private ProcessRepository processRepository;
	
	
	@GetMapping("/processes")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Process> getAllProcesses(){
		return processRepository.findAll();
	}
	
	@PostMapping("/addprocess")
	public Process createProcess(@RequestBody Process process) {
		return processRepository.save(process);
		
	}
	
	@GetMapping("/processes/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Process> getProcessById(@PathVariable Long id) {
		Process process= processRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("process with id : "+id+" DOES'NT EXIST"));
		return ResponseEntity.ok(process);
	}
	
	@PutMapping("/processes/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Process> updateProcess(@PathVariable Long id,@RequestBody Process processDetails){
		Process process= processRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Process with id : "+id+" DOES'NT EXIST"));
		
		process.setContainer_id(processDetails.getContainer_id());
		process.setName(processDetails.getName());
		process.setProcess_id(processDetails.getProcess_id());
		
		Process updatedprocess = processRepository.save(process);
		return ResponseEntity.ok(updatedprocess);
		}
	@DeleteMapping("/processes/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity< Map<String, Boolean>> deleteProcess(@PathVariable Long id){
		Process process= processRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Process with id : "+id+" DOES'NT EXIST"));
		processRepository.delete(process);
		Map<String,Boolean> response = new HashMap<>();
		response.put("DELETED", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	

}
