package com.test.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.model.Process;

public interface ProcessRepository extends JpaRepository<Process, Long> {

}
