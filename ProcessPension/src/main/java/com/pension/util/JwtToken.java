package com.pension.util;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pension.exception.JwtTokenEmptyException;
import com.pension.exception.JwtTokenExpiredException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtToken {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public void validateToken(final String token) throws JwtTokenExpiredException, JwtTokenEmptyException {
		try {
			Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret)).build()
					.parseClaimsJws(token);
		} catch (SecurityException ex) {
			throw new JwtTokenExpiredException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenExpiredException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenExpiredException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenExpiredException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenEmptyException("JWT string is empty.");
		}
	}

}
