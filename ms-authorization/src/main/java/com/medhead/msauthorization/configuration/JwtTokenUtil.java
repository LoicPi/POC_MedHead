package com.medhead.msauthorization.configuration;

import com.medhead.msauthorization.CustomProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import static java.lang.String.format;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;

    @Autowired
    private CustomProperties customProperties;

    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    //retrieve username from jwt token
	public String getUsernameFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(customProperties.getJwtSecret()).parseClaimsJws(token).getBody();

        return claims.getSubject().split(",")[0];
    }

    public Date getIssuedAtDateFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(customProperties.getJwtSecret()).parseClaimsJws(token).getBody();

        return claims.getIssuedAt();
    }

    //retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(customProperties.getJwtSecret()).parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    //check if the token has expired
	private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    //generate token for user
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(format("%s", user.getUsername()))
                .setIssuer(customProperties.getJwtIssuer()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, customProperties.getJwtSecret())
                .compact();
    }

    //while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, customProperties.getJwtSecret()).compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    //validate token
	public Boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(customProperties.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
