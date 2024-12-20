package com.example.Crix.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Bypass public endpoints
        String servletPath = request.getServletPath();
        if (servletPath.equals("/register") || servletPath.startsWith("/api/v1/auth/sendOtp") || servletPath.startsWith("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the token from the Authorization header
        String requestToken = request.getHeader("Authorization");

        String username = null;
        String token = null;

        // Validate token format
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7); // Remove "Bearer " prefix

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Jwt token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token has expired: " + e.getMessage());
            } catch (MalformedJwtException e) {
                System.out.println("Invalid Jwt token: " + e.getMessage());
            }
        } else {
            System.out.println("Jwt token does not begin with Bearer or is null");
        }

        // Validate token and authenticate user
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Invalid Jwt token");
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
