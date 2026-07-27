package com.attila.noteflow.controller;

import com.attila.noteflow.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
}
