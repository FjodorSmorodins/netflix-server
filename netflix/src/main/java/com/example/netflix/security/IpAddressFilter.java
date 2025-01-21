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

    private static final Set<String> ALLOWED_IPS = new HashSet<>();

    static {
        ALLOWED_IPS.add("134.209.199.147"); // Add allowed IP addresses
        // Add more IPs as needed
    }

    // Define a set of unrestricted URLs
    private static final Set<String> UNRESTRICTED_URLS = new HashSet<>();

    static {
        UNRESTRICTED_URLS.add("/");
        UNRESTRICTED_URLS.add("/index.html");
        UNRESTRICTED_URLS.add("/register");
        // Add more unrestricted URLs as needed
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        String requestUri = request.getRequestURI();

        // Skip IP filtering for unrestricted URLs
        if (UNRESTRICTED_URLS.contains(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Apply IP filtering for other URLs
        if (ALLOWED_IPS.contains(clientIp)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }
}