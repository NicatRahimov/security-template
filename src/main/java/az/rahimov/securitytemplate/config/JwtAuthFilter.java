package az.rahimov.securitytemplate.config;

import az.rahimov.securitytemplate.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                   @NotNull HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
final String authHeader=request.getHeader("Authorization");
final String jwt;
final String userEmail;
if (authHeader==null || !authHeader.startsWith("Bearer ")){
    filterChain.doFilter(request,response);
    return;
}
jwt=authHeader.substring(7);
userEmail=jwtService.extractUsername(jwt);
    }
}
