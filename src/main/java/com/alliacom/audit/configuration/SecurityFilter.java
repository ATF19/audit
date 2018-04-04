package com.alliacom.audit.configuration;

import com.alliacom.audit.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter implements Filter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authorization = httpServletRequest.getHeader("Authorization");
        String requestUrl = httpServletRequest.getRequestURI();
        if(!requestUrl.equals("/api/utilisateurs/admin/login")
        && !requestUrl.equals("/api/utilisateurs/login")
        && !requestUrl.startsWith("/api/rapports/")
        && !httpServletRequest.getMethod().equals("OPTIONS")) {
            if (authorization == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String token = authorization.replace("Bearer ", "");
            boolean validToken = tokenRepository.existsByTokenEquals(token);
            if (validToken == false) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
