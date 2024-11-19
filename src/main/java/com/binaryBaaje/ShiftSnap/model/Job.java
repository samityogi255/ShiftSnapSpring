package com.binaryBaaje.ShiftSnap.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long jobId;

	@Size(min=2, max=50, message= "Company Name must be between 2 and 50 characters")
	private String companyName;

	@Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters")
	private String position;

	@DecimalMin(value = "0.0", inclusive = false, message = "Pay rate must be greater than 0")
	private Double payRate;
	
	@NotEmpty(message = "Work history cannot be empty")
	private List<String> workHistory;
	
	@OneToMany(mappedBy= "job", cascade = CascadeType.ALL)
	private List<WorkSession> workSessions;
	
	 @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<Profile> profiles;
	
	public Job(Long jobId, String companyName, String position, Double payRate) {
		super();
		this.jobId = jobId;
		this.companyName = companyName;
		this.position = position;
		this.payRate = payRate;
	}

	public Job(){

	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}
	

	public List<String> getWorkHistory() {
		return workHistory;
	}

	public void setWorkHistory(List<String> workHistory) {
		this.workHistory = workHistory;
	}

	public List<WorkSession> getWorkSessions() {
		return workSessions;
	}

	public void setWorkSessions(List<WorkSession> workSessions) {
		this.workSessions = workSessions;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", companyName=" + companyName + ", position=" + position + ", payRate="
				+ payRate + "]";
	}
	
	
	
	



}
