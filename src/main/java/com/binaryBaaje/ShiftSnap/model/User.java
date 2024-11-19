package com.binaryBaaje.ShiftSnap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_table")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId; 
	
	
	@Size(min=2, max=50, message= "Name must be between 2 and 50 characters")
	private String name;
	
	@NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password; 
	
	@NotNull(message = "isNew cannot be null")
	private Boolean isNew;
	
	@OneToOne
	@JoinColumn(name="profile_id", referencedColumnName="profileId")
	private Profile profile;

	public User(Long userId,String name, String password, Boolean isNew) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.isNew = isNew;
	}
	
	public User() {
		
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", isNew=" + isNew + "]";
	}
	
	


}
