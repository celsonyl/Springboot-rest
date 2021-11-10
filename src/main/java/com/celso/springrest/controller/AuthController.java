package com.celso.springrest.controller;

import com.celso.springrest.security.AccountCredentials;
import com.celso.springrest.services.AuthenticateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticateService authenticateService;

    @ApiOperation(value = "Authenticate user")
    @PostMapping(value = "/signing", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> authenticate(@RequestBody AccountCredentials accountCredentials) {
        var authenticate = authenticateService.authenticate(accountCredentials);
        return ResponseEntity.ok().build();
    }
}
