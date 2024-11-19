package com.binaryBaaje.ShiftSnap.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.binaryBaaje.ShiftSnap.exception.JobNotFoundException;
import com.binaryBaaje.ShiftSnap.jpa.JobRepository;
import com.binaryBaaje.ShiftSnap.model.Job;

import jakarta.validation.Valid;



@RestController
public class JobController {

    private JobRepository jobRepository;

    public JobController(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }
    
    @GetMapping("/jobs")
    public List<Job> retreiveAllJobs() {
        return jobRepository.findAll();
    }

    @GetMapping("/jobs/{jobId}")
    public Optional<Job> retreiveAllJobs(@PathVariable Long jobId) throws JobNotFoundException {
        Optional<Job> job = jobRepository.findById(jobId);

        if(job.isEmpty()){
            throw new JobNotFoundException("JobId: " + jobId);
        }
        return job;
    }

    @PostMapping("/jobs")
    public ResponseEntity<Object> createJob(@Valid @RequestBody Job job){
        Job savedJob = jobRepository.save(job);

        URI location = ServletUriComponentsBuilder
                                        .fromCurrentRequest()
                                        .path("/jobId")
                                        .buildAndExpand(savedJob.getJobId())
                                        .toUri();
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/jobs/{jobId}")
    public void deleteJob(@PathVariable Long jobId){
        jobRepository.deleteById(jobId);
    }

    @PostMapping("/jobs/{jobId}")
    public ResponseEntity<Object> updateJob(@Valid @RequestBody Job job){
        Job savedJob = jobRepository.save(job);

        URI location = ServletUriComponentsBuilder
                                        .fromCurrentRequest()
                                        .path("/jobId")
                                        .buildAndExpand(savedJob.getJobId())
                                        .toUri();
        return ResponseEntity.created(location).build();

    }
}
