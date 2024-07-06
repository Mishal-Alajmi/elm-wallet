package com.malajmi.elm_wallet.user;

import com.malajmi.elm_wallet.user.models.LoginRequest;
import com.malajmi.elm_wallet.user.models.LoginResponse;
import com.malajmi.elm_wallet.user.models.SignupRequest;
import com.malajmi.elm_wallet.user.models.WalletUserResponse;
import com.malajmi.elm_wallet.utils.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public WalletUserResponse signup(@Valid @RequestBody SignupRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var token = JwtHelper.generateToken(request.username());
        return LoginResponse.builder()
                .username(request.username())
                .token(token)
                .build();
    }
}
