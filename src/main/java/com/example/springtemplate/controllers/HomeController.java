package com.example.springtemplate.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles all the requests that begins with /home
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/public")
    public ResponseEntity<String> publicUser(){
        return ResponseEntity.ok("Public user");
    }
    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return ResponseEntity.ok("Registered user");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> regulators(){
        return ResponseEntity.ok("<h1>admin user</h1>");
    }
}
