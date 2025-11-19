package br.com.guilhelp.controller;

import br.com.guilhelp.dtos.LoginDto;
import br.com.guilhelp.dtos.RegisterDto;
import br.com.guilhelp.dtos.TokenDto;
import br.com.guilhelp.errors.DuplicateEmailException;
import br.com.guilhelp.repositories.UserRepository;
import br.com.guilhelp.security.MongoUserDetails;
import br.com.guilhelp.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }


    @PostMapping("/register")
    public TokenDto register(@RequestBody @Valid RegisterDto registerDto) {
        var user = registerDto.toDomain();
        try {
            var savedUser = userRepository.save(user);
            return new TokenDto(tokenService.generateToken(savedUser));
        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException("Email already in use");
        }
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        var authenticationToken = loginDto.toAuthenticationToken();
        var authentication = authenticationManager.authenticate(authenticationToken);
        var userDetails = (MongoUserDetails) authentication.getPrincipal();
        var token = tokenService.generateToken(userDetails.getUser());
        return new TokenDto(token);
    }
}
