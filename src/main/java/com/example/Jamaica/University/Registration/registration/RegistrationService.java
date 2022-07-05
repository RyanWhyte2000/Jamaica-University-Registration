package com.example.Jamaica.University.Registration.registration;

import com.example.Jamaica.University.Registration.user.AppUser;
import com.example.Jamaica.University.Registration.user.UserRole;
import com.example.Jamaica.University.Registration.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private  EmailValidator emailValidator;
    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail){
            throw new IllegalStateException("Email not valid");

        }
        return userService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER)
        );
    }
}
