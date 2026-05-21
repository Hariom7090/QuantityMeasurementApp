package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(
            JwtService jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User)
                        authentication.getPrincipal();

        String email =
                oauthUser.getAttribute(
                        "email"
                );

        String name =
                oauthUser.getAttribute(
                        "name"
                );

        String picture =
                oauthUser.getAttribute(
                        "picture"
                );

        String token =
                jwtService.generateToken(
                        email,
                        name,
                        picture
                );

        response.setContentType(
                "application/json"
        );

        response.getWriter().write(
                """
                {
                    "token":"%s",
                    "email":"%s",
                    "name":"%s",
                    "picture":"%s"
                }
                """.formatted(
                        token,
                        email,
                        name,
                        picture
                )
        );
    }
}