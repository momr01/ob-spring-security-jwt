package ar.com.momr.obspringsecurityjwt.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

/**
 * Métodos para generar y validar los token JWT
 */
@Component
public class JwtTokenUtil {

	private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration-ms}")
	private int jwtExpirationMs;

	/**
	 * 
	 * The signing key's size is 24 bits which is not secure enough for the HS512
	 * algorithm. The JWT JWA Specification (RFC 7518, Section 3.2) states that keys
	 * used with HS512 MUST have a size >= 512 bits (the key size must be greater
	 * than or equal to the hash output size). Consider using the
	 * io.jsonwebtoken.security.Keys class's
	 * 'secretKeyFor(SignatureAlgorithm.HS512)' method to create a key guaranteed to
	 * be secure enough for HS512.
	 * 
	 * @return
	 */
	/*
	public static Key getKey() {
		  return Keys.secretKeyFor(SignatureAlgorithm.HS512);
		}
*/
	
	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				// .signWith(SignatureAlgorithm.HS512, jwtSecret)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
