package com.gochat.security;

import com.gochat.domain.UserToken;

/**
 * @author mpcariaso
 *
 */
public interface UserLoginServiceClient {

	public UserToken login(String userName, String password);

}