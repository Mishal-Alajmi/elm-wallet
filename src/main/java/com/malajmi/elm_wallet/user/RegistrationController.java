package com.malajmi.elm_wallet.user;

import com.malajmi.elm_wallet.user.models.LoginRequest;
import com.malajmi.elm_wallet.user.models.LoginResponse;
import com.malajmi.elm_wallet.user.models.SignupRequest;
import com.malajmi.elm_wallet.user.models.WalletUserResponse;
import com.malajmi.elm_wallet.utils.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<WalletUserResponse> signup(@Valid @RequestBody SignupRequest request) {
        var user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        var token = JwtHelper.generateToken(request.username());
        return ResponseEntity.ok(new LoginResponse(request.username(), token));
    }
}
