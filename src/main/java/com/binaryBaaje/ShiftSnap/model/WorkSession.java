package com.binaryBaaje.ShiftSnap.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WorkSession {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long sessionId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime date;
	
    @ManyToOne
	@JoinColumn(name = "job_id", referencedColumnName = "jobId")
	private Job job;
	
	public WorkSession(Long sessionId, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime date) {
		super();
		this.sessionId = sessionId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
	}

	public Long getSessonId() {
		return sessionId;
	}

	public void setSessonId(Long sessonId) {
		this.sessionId = sessonId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "WorkSession [sessonId=" + sessionId + ", startTime=" + startTime + ", endTime=" + endTime + ", date="
				+ date + "]";
	}
	
	
	
	

}
