package com.pytorch.yolv5.controllers;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.pytorch.yolv5.services.YoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
public class YolovController {

    @Autowired
    YoloService yoloService;

    @PostMapping("/scores")
    public ResponseEntity<String> getScores(@RequestBody String image) throws ModelNotFoundException, MalformedModelException, IOException {
        return new ResponseEntity<>(yoloService.getScores(image), HttpStatus.ACCEPTED);
    }
}
