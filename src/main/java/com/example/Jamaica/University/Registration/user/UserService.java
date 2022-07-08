package com.example.Jamaica.University.Registration.user;

import com.example.Jamaica.University.Registration.registration.token.ConfirmationToken;
import com.example.Jamaica.University.Registration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private  final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.trace("Enter Method loadUserByUsername");

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }
    public String signUpUser(AppUser user){
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if(userExists){
            throw new IllegalStateException("Email Already taken");
        }
     String encodedPassword =   bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = java.util.UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        // TODO: SEND EMAIL
        return token;
    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
