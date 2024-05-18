package br.com.fiap.trafficManagement.controller;

import br.com.fiap.trafficManagement.config.security.TokenConfig;
import br.com.fiap.trafficManagement.dto.LoginDto;
import br.com.fiap.trafficManagement.dto.TokenDto;
import br.com.fiap.trafficManagement.dto.UserExhibitionDto;
import br.com.fiap.trafficManagement.dto.UserInsertDto;
import br.com.fiap.trafficManagement.model.User;
import br.com.fiap.trafficManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenConfig.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserExhibitionDto register(@RequestBody @Valid UserInsertDto userInsertDto) {
        UserExhibitionDto userSaved = null;
        userSaved = userService.insertUser(userInsertDto);
        return userSaved;
    }
}
