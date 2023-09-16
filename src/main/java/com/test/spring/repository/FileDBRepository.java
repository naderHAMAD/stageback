package com.test.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.model.FileDB;

public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
