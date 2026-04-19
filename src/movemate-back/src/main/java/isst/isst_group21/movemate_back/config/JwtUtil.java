package isst.isst_group21.movemate_back.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.util.Date;


@Component
public class JwtUtil {

    // Clave secreta para firmar el token
    private final String SECRET_KEY_STRING = "5aV3sUperS3creTaMovemateJWT@2025!";
    private Key SECRET_KEY;

    // Tiempo de expiración del token
    private final long EXPIRATION_TIME = 86400000; //en milisegundos

    @PostConstruct
    public void init() {
        this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    }

    public String generateToken(String email, String role, String id) {
        return Jwts.builder()
                .setSubject(email) 
                .claim("role", role) 
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(SECRET_KEY) //con HS256 y la clave secreta
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }

    public String getIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", String.class);
    }
}

