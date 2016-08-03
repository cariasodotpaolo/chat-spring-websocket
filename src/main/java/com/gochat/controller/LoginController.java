package com.gochat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gochat.domain.UserToken;
import com.gochat.exceptions.InvalidAcceptHeaderException;
import com.gochat.security.UserLoginServiceClient;

/**
 * @author mpcariaso
 *
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    private UserLoginServiceClient loginClient;
	
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
    public String indexPage() {
       
		return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, 
    					HttpServletRequest request, 
    					HttpServletResponse response) throws Exception {
        
    		
    	
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String keepSignedIn = request.getParameter("keepSignedIn");
        
        logger.debug("/login request from user: " + userName);    
        
        UserToken userToken = loginClient.login(userName, password);
        
        if(userToken.getAccessToken() != null) {
            HttpSession session = request.getSession();
            session.setAttribute("keepSignedIn", keepSignedIn);
            session.setAttribute("userName", userName);
            session.setAttribute("accessToken", userToken.getAccessToken());
            session.setAttribute("acceptHeader", "application/gochat+secretkey");
                                    
            logger.debug("accessToken: " + userToken.getAccessToken());
                        
            model.addAttribute("userName", userName);
            return "forward:/chat/home";
        } else {                  	
        	model.addAttribute("errorMessage", "Invalid username and/or password.");
        	return "login";        	
        }
       
    }
    
    
	
	/*
	 * the reason value becomes the response body.
	 * It should not provide any clue on why this exception/Status code was returned
	 * for security purposes
	 */
	@ExceptionHandler(InvalidAcceptHeaderException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason="Not Acceptable")
	public void handleInvalidAcceptHeaderException(InvalidAcceptHeaderException e) {    
		
		//TODO: do some logging here
		//logger.error(e.getMessage());
	}
	
}
