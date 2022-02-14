package com.investree.demo.controller;


import com.investree.demo.model.RegisterModel;
import com.investree.demo.model.oauth.User;
import com.investree.demo.view.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/registerStaff")
    public ResponseEntity<Map> save(@RequestBody RegisterModel registerModel){
        Map save = registerService.registerManual(registerModel);
        return new ResponseEntity<Map>(save, HttpStatus.OK);
    }
}
