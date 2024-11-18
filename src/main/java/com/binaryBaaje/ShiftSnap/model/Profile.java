package com.binaryBaaje.ShiftSnap.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue
	private Long profileId; 
	private String name;
	private String profilePic;
	
	 @ManyToOne
	 @JoinColumn(name = "job_id")
	 private Job job;

	
	@OneToOne(mappedBy="profile")
	private User user;
	
	public Profile(Long profileId, String name, String profilePic) {
		super();
		this.profileId = profileId;
		this.name = name;
		this.profilePic = profilePic;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Profile [profileId=" + profileId + ", name=" + name + ", profilePic=" + profilePic + "]";
	}
	
	
	
	

}
