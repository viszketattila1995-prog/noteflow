package com.attila.noteflow.service;

import com.attila.noteflow.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
}
