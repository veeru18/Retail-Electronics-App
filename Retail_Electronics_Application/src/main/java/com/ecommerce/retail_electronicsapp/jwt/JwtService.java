package com.ecommerce.retail_electronicsapp.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${myapp.jwt.secret}")
	private String secret;
	
	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;
	
	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;
	
	private Key getSignatureKeys() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	
	public String generateAccessToken(String username, String role) {
		return generateToken(username, role, accessExpiration);
	}
	
	public String generateRefreshToken(String username, String role) {
		return generateToken(username, role, refreshExpiration);
	}
	
	private String generateToken(String username, String role, long expiration) {
		return Jwts.builder()
				.setClaims(Maps.of("role",role).build()) 
//				 this doesn't initialize the claims,should be at start before setting other claims
				.setSubject(username)
//				.claim("username", username) //adding individually claims
//				.addClaims(new HashMap<String,Object>()) 
//				 this addClaims() can be anywhere in method chaining to set claims like createdAt expiration and username
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSignatureKeys(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getUsername(String token) {
		return parseJwtToken(token).getSubject();
	}
	
	private Claims parseJwtToken(String jwtToken) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKeys())
				.build()
				//returns JWTParser object which has the secret key of app
				.parseClaimsJws(jwtToken)
				//this method compares app signature and passed token signature 
				.getBody();
		
		//handle jwtparser exceptions,jwtexception -invalid jwt message, jwtExpiredException - token has expired
	}
	
	public String getUserRole(String token) {
		return parseJwtToken(token).get("role", String.class);
	}
}
