package isst.isst_group21.movemate_back.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter  {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println("[JWT Filter] Interceptando: " + request.getRequestURI());
        System.out.println("[JWT Filter] Authorization header: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                // Token inválido -> Bloquear
                System.out.println("[JWT Filter] Token inválido");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtUtil.getEmailFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            request.setAttribute("role", role); 
            request.setAttribute("email", email); 
            
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            // No hay token -> Bloquear
            System.out.println("[JWT Filter] No hay token, se bloquea");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean excluded = path.contains("/registro") || path.contains("/login") || path.contains("/h2-console") || path.startsWith("/img/");;
        System.out.println("[shouldNotFilter] path: " + path + " | excluded: " + excluded);
        return excluded;
    }
}

