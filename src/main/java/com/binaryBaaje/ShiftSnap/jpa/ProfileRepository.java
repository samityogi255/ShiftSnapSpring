package com.binaryBaaje.ShiftSnap.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.binaryBaaje.ShiftSnap.model.Profile;

@Repository
public interface  ProfileRepository extends JpaRepository<Profile, Long> {
    
}
