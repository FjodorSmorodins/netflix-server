package com.example.netflix.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class IpAddressFilter extends OncePerRequestFilter {

    // Define a set of allowed IP addresses
    private static final Set<String> ALLOWED_IPS = new HashSet<>();

    static {
        ALLOWED_IPS.add("134.209.199.147"); // Replace with your own allowed IPs
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String clientIp = request.getRemoteAddr();

        if (ALLOWED_IPS.contains(clientIp)) {
            // Proceed with the request if the IP is allowed
            filterChain.doFilter(request, response);
        } else {
            // Deny the request if the IP is not allowed
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }
}