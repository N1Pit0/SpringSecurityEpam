package com.mygym.crm.backstages.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/trainees")
public class TraineeController {

    @GetMapping(value = "")
    public ResponseEntity<String> getTrainees(){
        String sd = "dsa";
        return new ResponseEntity<>(sd, HttpStatus.OK);
    }

}
