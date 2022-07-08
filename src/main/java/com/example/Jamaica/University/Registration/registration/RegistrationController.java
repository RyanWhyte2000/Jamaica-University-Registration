package com.example.Jamaica.University.Registration.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;
    public String register(@RequestBody RegistrationRequest request){
        log.trace("Enter Method Register");
        return registrationService.register(request);
    }
    @GetMapping(path = "confirm")
    public String confirm (@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
