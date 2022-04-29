package usta.com.jwtdemo.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class jwtUtility implements Serializable {
    private static final long serialVersionUID = 234324523523L;

    public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60;

    @Value("${jwt.secret}")
    private String secretKEy;

    // Traer usernema del jwt token
    public String getUserNameFromTowken(String token ){
        return getClaimFromToken(token, Claims::getSubject);
    }

    //Obtener fecha de expiracion del jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    //Metodo para traer cualquier informacion del token que necesite una llave secreta(secret key)
    private Claims getAllClaimFromToken(String token){
        return Jwts.parser().setSigningKey(secretKEy).parseClaimsJws(token).getBody();
    }

    //Verificar si el token ha expirado
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Generar el token para el usuario
    public String generateToken (UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //Creacion de token
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 100)).
                signWith(SignatureAlgorithm.HS512, secretKEy).compact();
    }

    //Validar el token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserNameFromTowken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
