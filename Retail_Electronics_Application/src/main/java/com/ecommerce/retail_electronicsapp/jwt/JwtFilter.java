package com.ecommerce.retail_electronicsapp.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.retail_electronicsapp.exceptions.AccountAceessRequestDeniedException;
import com.ecommerce.retail_electronicsapp.repository.AccessTokenRepository;
import com.ecommerce.retail_electronicsapp.repository.RefreshTokenRepository;
import com.ecommerce.retail_electronicsapp.utility.TokenResponseStructure;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private AccessTokenRepository accessRepo;
	private RefreshTokenRepository refreshRepo;
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("inside jwt filter");
		Cookie[] cookies = request.getCookies();
		String accessToken=null;
		String refreshToken=null;
//		String refreshToken=Arrays.stream(request.getCookies())
//							.map(cookie->cookie.getAttribute("rt"))
//							.collect(Collectors.toList())
//							.get(0);

		if(cookies!=null) {
			System.out.println("cookies are present");
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("at"))
					accessToken=cookie.getValue();
				else if(cookie.getName().equals("rt"))
					refreshToken=cookie.getValue();
			}
		}

		String userRole=null,username=null;
		if(accessToken!=null && refreshToken!=null) {
			System.out.println("both tokens are present");
			try {
	            if(accessRepo.existsByTokenAndIsBlockedTrue(accessToken)
	                    || refreshRepo.existsByTokenAndIsBlockedTrue(refreshToken)) 
	                throw new AccountAceessRequestDeniedException("Access for this account from these tokens is blocked");

	            else {
	                username=jwtService.getUsername(accessToken);
	                System.out.println(username);
	                userRole=jwtService.getUserRole(accessToken);
	                System.out.println(userRole);
	                if (username != null && userRole != null) {
	                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	                    if (authentication == null || !authentication.isAuthenticated()) {
	                        UsernamePasswordAuthenticationToken token =
	                                new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(userRole)));
	                        token.setDetails(new WebAuthenticationDetails(request));
	                        SecurityContextHolder.getContext().setAuthentication(token);
	                        System.out.println(token.getName());
	                        System.out.println("authentication object was null");
	                    }
	                }
	            }
	        } 
			catch (ExpiredJwtException e) {
	            // Handling JWTExpired exception
	        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
	            Map<String, String> errors = new HashMap<>();
	            errors.put("error", "Unauthorized, access token has expired");
	            errors.put("message", e.getMessage());
	            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	            new ObjectMapper().writeValue(response.getOutputStream(),
	            		TokenResponseStructure.failure(errors));
	            return;
	        } catch (JwtException e) {
	            // Handling JWT exception
	        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
	            Map<String, String> errors = new HashMap<>();
	            errors.put("error", "Unauthorized, malformed token or invalid signature");
	            errors.put("message", e.getMessage());
	            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	            new ObjectMapper().writeValue(response.getOutputStream(),
	                    TokenResponseStructure.failure(errors));
	            return;
	        }
		}
//		else even if one of the tokens are empty allow to pass through other filters since user might be trying to login thus allow

		filterChain.doFilter(request, response);
	}
}
