package com.gochat.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

/**
 * @author mpcariaso
 *
 */
@Service
@Scope("singleton")
public class TokenVerificationServiceImpl implements TokenVerificationService {

	@Resource
	private DefaultTokenServices tokenServices;

	/* (non-Javadoc)
	 * @see com.gochat.service.TokenVerificationService#isAuthorized(java.lang.String)
	 */
	@Override
	public boolean isAuthorized(String token) {
		
		OAuth2AccessToken oauth2Token = tokenServices.readAccessToken(token);
		
		if(oauth2Token != null && oauth2Token.getValue().equals(token)) {
			return true;
		} else return false;
	}
	
	@Override
	public void revokeToken(String accessToken) {
		
		tokenServices.revokeToken(accessToken);
	}
}
