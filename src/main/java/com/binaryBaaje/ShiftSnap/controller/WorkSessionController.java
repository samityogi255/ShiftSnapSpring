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

import com.binaryBaaje.ShiftSnap.exception.SessionNotFoundException;
import com.binaryBaaje.ShiftSnap.jpa.WorkSessoionRepository;
import com.binaryBaaje.ShiftSnap.model.WorkSession;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class WorkSessionController {
    
    private WorkSessoionRepository workSessoionRepository;

    public WorkSessionController(WorkSessoionRepository workSessoionRepository){
        this.workSessoionRepository = workSessoionRepository;
    }

    @GetMapping("/worksessions")
    public ResponseEntity<List<WorkSession>> retreiveWorkSessions(){
       return new ResponseEntity<>(workSessoionRepository.findAll(), HttpStatus.OK);
        }

    @GetMapping("/worksessions/{sessionId}")
    public Optional<WorkSession> retreiveWorkSessionId(@PathVariable Long sessionId) throws SessionNotFoundException{
        Optional<WorkSession> sessions= workSessoionRepository.findById(sessionId);

        if(sessions.isEmpty()){
            throw new SessionNotFoundException("SessionId: "+ sessionId);
        }
        return sessions;

    }

    @PostMapping("/worksessions")
    public ResponseEntity<Object> createSession(@Valid @RequestBody WorkSession workSession){
        WorkSession savedSession = workSessoionRepository.save(workSession);

        URI location = ServletUriComponentsBuilder
                                        .fromCurrentRequest()
                                        .path("/sessionId")
                                        .buildAndExpand(savedSession.getSessonId())
                                        .toUri();
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/worksessions/{sessionId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long sessionId){
        Optional<WorkSession> workSession = workSessoionRepository.findById(sessionId);
        if(workSession.isPresent()){
            workSessoionRepository.deleteById(sessionId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/worksessions/{sessionId}")
    public ResponseEntity<Object> updateSession(@Valid @RequestBody WorkSession workSession, @PathVariable Long sessionId){
      if(workSessoionRepository.existsById(sessionId)){
        workSession.setSessonId(sessionId);
        workSessoionRepository.save(workSession);
        return new ResponseEntity<>("Work Session is updated", HttpStatus.OK);
      }
      return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
}
}

