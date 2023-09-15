package com.aces.spring.login.security.jwt;
import com.aces.spring.login.security.services.UserDetailsImpl;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${aces.app.jwtSecret}")
  private String jwtSecret;

  @Value("${aces.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${aces.app.jwtCookieName}")
  private String jwtCookie;

  private String JWT_SECRET="SECRET_KEY";

  public String getJwtFromCookies(HttpServletRequest request) {
    if(request.getCookies() == null)
            return null;

        Cookie[] cookies = request.getCookies();
        
        for(Cookie cookie: cookies){
            if(cookie.getName().equals(jwtCookie))
                return cookie.getValue();
        }

        return null;
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).secure(false).sameSite("None").build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
    return cookie;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(JWT_SECRET.getBytes()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  private Key key() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS256); // Use secure key generation
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(JWT_SECRET.getBytes()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    } catch(RuntimeException e){
      logger.error("Some JWT Parsing Error...");
    }

    return false;
  }

  public String generateTokenFromUsername(String username) {
    return Jwts.builder()
              .setSubject(username)
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
              .compact();
  }
}
