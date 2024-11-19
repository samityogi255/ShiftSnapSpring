package com.binaryBaaje.ShiftSnap.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.binaryBaaje.ShiftSnap.model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{
    
}
