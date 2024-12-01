package com.binaryBaaje.ShiftSnap.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.binaryBaaje.ShiftSnap.exception.JobNotFoundException;
import com.binaryBaaje.ShiftSnap.jpa.JobRepository;
import com.binaryBaaje.ShiftSnap.model.Job;

import jakarta.validation.Valid;



@RestController
@CrossOrigin
public class JobController {

    private JobRepository jobRepository;

    public JobController(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }
    
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> retreiveAllJobs() {
        return new ResponseEntity<>(jobRepository.findAll(), HttpStatus.OK);
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
    public ResponseEntity<String>deleteJob(@PathVariable Long jobId){
        Optional<Job> job = jobRepository.findById(jobId);
        if(job.isPresent()){
            jobRepository.deleteById(jobId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } 
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<String> updateJob(@Valid @RequestBody Job job, @PathVariable Long jobId){
        if(jobRepository.existsById(jobId)){
            job.setJobId(jobId);
            jobRepository.save(job);
            return new ResponseEntity<>("Job Updated Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Job Not Found", HttpStatus.NOT_FOUND);
    }
}
