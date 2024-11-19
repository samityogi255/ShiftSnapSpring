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

import com.binaryBaaje.ShiftSnap.exception.ProfileNotFoundException;
import com.binaryBaaje.ShiftSnap.jpa.ProfileRepository;
import com.binaryBaaje.ShiftSnap.model.Profile;

import jakarta.validation.Valid;

@RestController
public class ProfileController {

   private ProfileRepository profileRepository;

   public ProfileController(ProfileRepository profileRepository){
    this.profileRepository = profileRepository;
   }

   @GetMapping("/profiles")
   public List<Profile> retreiveAllProfiles(){
    return profileRepository.findAll();
   }

   @GetMapping("/profiles/{profileId}")
   public Optional<Profile> retreiveProfile( @PathVariable Long profileId) throws ProfileNotFoundException{
    Optional<Profile> profile = profileRepository.findById(profileId);

    if(profile.isEmpty()){
        throw new ProfileNotFoundException("profileId: " + profileId);
    }

    return profile;

   }

   @PostMapping("/profiles")
   public ResponseEntity<Object> addNewProfile( @Valid @RequestBody Profile profile){
    Profile savedProfile =profileRepository.save(profile);

    URI location =  ServletUriComponentsBuilder
                                    .fromCurrentRequest()
                                    .path("{profileId}")
                                    .buildAndExpand(savedProfile.getProfileId())
                                    .toUri();
    return ResponseEntity.created(location).build();
    
   }

   @DeleteMapping("/profiles/{profileId}")
   public void deleteProfile(@PathVariable Long profileId){
    profileRepository.deleteById(profileId);
   }

   @PostMapping("/profiles/{profileId}")
   public ResponseEntity<Object> updateProfile( @Valid @RequestBody Profile profile){
    Profile savedProfile =profileRepository.save(profile);

    URI location =  ServletUriComponentsBuilder
                                    .fromCurrentRequest()
                                    .path("{profileId}")
                                    .buildAndExpand(savedProfile.getProfileId())
                                    .toUri();
    return ResponseEntity.created(location).build();
    
   }

    
}