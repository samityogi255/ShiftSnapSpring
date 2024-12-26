package com.binaryBaaje.ShiftSnap.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.binaryBaaje.ShiftSnap.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    //User findByEmail(String email);
    Optional<User> findByEmail(String email);
}
