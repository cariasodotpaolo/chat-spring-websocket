package com.gochat.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author mpcariaso
 *
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

	/*@Autowired
	CredentialValidator credentialValidator;*/
	
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);
	
	private UserDetailsService userDetailsService;
	
	private PasswordEncoder passwordEncoder;
	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {		
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());		
		String givenPassword = authentication.getCredentials().toString();
		boolean passwordMatched = passwordEncoder.matches(givenPassword, userDetails.getPassword());			
		
		if (userDetails != null && passwordMatched) {
			
			UserAuthenticationToken auth =	new UserAuthenticationToken(userDetails.getUsername(),
																		userDetails.getPassword(), 
																		userDetails.getAuthorities()); 

			return auth;
			
		} else {
			
			throw new BadCredentialsException("Incorrect username AND/OR password.");			
		} 
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
