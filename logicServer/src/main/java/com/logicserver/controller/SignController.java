package com.logicserver.controller;

import com.logicserver.dto.SignRequest;
import com.logicserver.dto.SignResponse;
import com.logicserver.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping(value = {"/login", "/login/"})
    public SignResponse signin(@RequestBody SignRequest request) throws Exception {
        return signService.login(request);
    }

    @PostMapping(value = {"/register", "/register/"})
    public Boolean signup(@RequestBody SignRequest request) throws Exception {
        return signService.register(request);
    }

    @GetMapping(value = {"/user/find", "/user/find/"})
    public SignResponse findUser(@RequestParam String email) throws Exception {
        return signService.getMember(email);
    }
}