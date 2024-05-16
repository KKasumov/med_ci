package com.kasumov.med_ci.controller;

import com.kasumov.med_ci.model.dto.user.patient.PatientDto;
import com.kasumov.med_ci.config.security.jwt.JwtResponse;
import com.kasumov.med_ci.config.security.jwt.JwtUtils;
import com.kasumov.med_ci.config.security.jwt.LoginRequest;
import com.kasumov.med_ci.model.entity.user.Invite;
import com.kasumov.med_ci.model.entity.user.User;
import com.kasumov.med_ci.model.exception.EntityNotFoundException;
import com.kasumov.med_ci.model.exception.InvalidParametersPassedException;
import com.kasumov.med_ci.model.exception.OverdueDateException;
import com.kasumov.med_ci.model.response.Response;
import com.kasumov.med_ci.service.entity.user.InviteService;
import com.kasumov.med_ci.service.entity.user.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PatientService patientService;
    private final InviteService inviteService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }

    @ApiOperation("Установка пароля, полученного от пользователя после его прохождения по ссылке")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пароль установлен"),
            @ApiResponse(code = 450, message = "Ссылка устарела"),
            @ApiResponse(code = 452, message = "Ссылка устарела"),
            @ApiResponse(code = 453, message = "Пароль не соответствует минимальным требованиям")
    })
    @PatchMapping("/password/change")
    public Response<PatientDto> passwordSetByUser(@RequestParam(name = "token") String token,
                                                  @RequestParam(name = "password") String password) {

        Invite invite = inviteService.getInviteByToken(token);
        password = password.trim();
        if (password.length() < 10 || !password.matches("^[a-zA-Z0-9]*$")) {
            throw new InvalidParametersPassedException("Пароль не соответствует минимальным требованиям");
        }
        if (invite == null) {
            throw new EntityNotFoundException("Ссылка устарела");
        }
        if (invite.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new OverdueDateException("Ссылка устарела");
        }
        return Response.ok(patientService.setPasswordGetFromUserAndDeleteInvite(invite, password));
    }
}
