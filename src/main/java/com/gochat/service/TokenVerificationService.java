package com.gochat.service;

/**
 * @author mpcariaso
 *
 */
public interface TokenVerificationService {

	boolean isAuthorized(String token);

	void revokeToken(String accessToken);

}