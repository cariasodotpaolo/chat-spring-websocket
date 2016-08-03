package com.gochat.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author mpcariaso
 *
 */
public class UserAuthenticationToken extends AbstractAuthenticationToken {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 7214490714957226019L;
	
	private final Object principal;
    private Object credentials;
 
    public UserAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }
 
    public Object getCredentials() {
        return this.credentials;
    }
 
    public Object getPrincipal() {
        return this.principal;
    }
}
