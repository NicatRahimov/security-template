package az.rahimov.securitytemplate.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "TmljYXRSYWhpbW92MTY=";


    public String generateToken(
            UserDetails userDetails
    ){
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+180000))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <R> R extractClaims(String token, Function<Claims,R> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

}

private Date extractExpireDate(String token){
       return extractAllClaims(token).getExpiration();
}

    private boolean isTokenExpired(String token) {
       return extractExpireDate(token).before(new Date());
    }

    private Key getSignInKey() {
        byte[] decoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decoded);
    }

}
