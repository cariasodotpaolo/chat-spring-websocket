package com.gochat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gochat.domain.ChatChannel;
import com.gochat.domain.ChatUser;
import com.gochat.exceptions.InvalidAcceptHeaderException;
import com.gochat.service.ChannelService;
import com.gochat.service.OnlineService;
import com.gochat.service.TokenVerificationService;
import com.gochat.service.UserService;

/**
 * @author mpcariaso
 *
 */
@Controller
@RequestMapping(value = "/chat")
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private OnlineService onlineService;
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private TokenVerificationService tokenVerificationService;
			
	@RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView getHomePage(HttpServletRequest request, 
    								HttpServletResponse response) throws Exception {
		
		String accessToken = (String)request.getSession(false).getAttribute("accessToken");		
		
		logger.debug("accessToken: " + accessToken);		
				
		String userName = ( String ) request.getAttribute( "userName" );
		ModelAndView model = new ModelAndView( "home" );
		
		ChatUser chatUser = userService.getUserByUserName(userName);
		onlineService.setOnline(chatUser);
        
		HttpSession session = request.getSession(false);
		session.setAttribute("chatUser", chatUser);
		
        List<ChatUser> chatList = userService.getUserChatList( userName );
        List<ChatChannel> channelList = channelService.getChannels();
                
        model.getModelMap().addAttribute("accessToken", accessToken);
        model.getModelMap().addAttribute( "chatList" , chatList );
        model.getModelMap().addAttribute("channels", channelList);
        
		return model;
        
    }
	
	@RequestMapping(value = "/channel/{channel}", method = RequestMethod.GET)
	public ModelAndView joinChannel(HttpServletRequest request,
									@PathVariable String channel,
									@QueryParam("channelDisplay") String channelDisplay) {
		
		logger.debug("Entering channel: " + channel + ", Display: " + channelDisplay);
		
		HttpSession session = request.getSession(false);
		ChatUser chatUser = (ChatUser) session.getAttribute("chatUser");
		String accessToken = (String) session.getAttribute("accessToken");
		
		logger.debug("Chat User: " + chatUser.getUserName() + " joined channel: " + channel);
		
		ModelAndView model = new ModelAndView("chat-channel");
		model.getModelMap().addAttribute("accessToken", accessToken);
		model.getModelMap().addAttribute("channel", channel);
		model.getModelMap().addAttribute("channelDisplay", channelDisplay);
		model.getModelMap().addAttribute("chatUserName", chatUser.getUserName());
		model.getModelMap().addAttribute("chatUserFullName", chatUser.getFullName());
		
		return model;
		
	}
	
	/*
	 * Secure API for establishing connection to websocket endpoint
	 * @ResponseBody prevents resolving string value to JSP/View name
	 */
	@RequestMapping(value="/server/channel/{channel}", 
					method= RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String connectToChatServer(HttpServletRequest request,
									  @PathVariable String channel,
									  @RequestHeader("Authorization") String authHeader) {		
		
		//NOTE: Bearer has capital B - OAuth specification
		String headerToken = authHeader.split("Bearer")[1].trim();
				
		HttpSession session = request.getSession(false);		
		String accessToken = (String) session.getAttribute("accessToken");
		
		logger.debug("session token: " + accessToken);
		logger.debug("header token: " + headerToken);
		
		if(!accessToken.equals(headerToken)) {
			tokenVerificationService.revokeToken(accessToken);
			onlineService.setOffline((String)session.getAttribute("userName"));
			session.invalidate();
			throw new UnauthorizedClientException("Invalid token");
		}
		
		String websocketUrl = "ws://" + request.getServerName() + ":" + request.getServerPort() + 
							  request.getContextPath() + "/endpoint/channel/" + channel + "/" + headerToken;
		
		logger.debug("Opening websocket session from: " + websocketUrl);
		
		return websocketUrl;
	}
	
	@RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        
    	String userName = request.getParameter("userName");
    	
    	//ChatUser chatUser = (ChatUser) request.getSession().getAttribute("chatUser");
    	
    	onlineService.setOffline(userName);
    	
        HttpSession session = request.getSession(false);
        String accessToken = (String) session.getAttribute("accessToken");
        
        tokenVerificationService.revokeToken(accessToken);
        
        session.invalidate();
       
        return "login";
    }
	
	/*@MessageMapping("/message/channel/{channel}")
	@SendTo("/server/channel/{channel}")
	public Message sendMessage(@DestinationVariable String channel) {
		
		return new Message();
	}*/
	
	@ExceptionHandler(InvalidAcceptHeaderException.class)
	//@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Acceptable")
	public ModelAndView handleInvalidAcceptHeaderException(HttpServletResponse response,
														   InvalidAcceptHeaderException e) {

		// TODO: do some logging here
		logger.error(e.getMessage(), e);
		response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		ModelAndView model = new ModelAndView("error-page");
		model.getModelMap().addAttribute("errorMessage", e.getMessage());
		
		return model;
	}
	
	@ExceptionHandler(UnauthorizedClientException.class)
	//@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
	public ModelAndView handleUnauthorizedException(HttpServletResponse response, 
												    UnauthorizedClientException e) {
		
		// TODO: do some logging here
		logger.error(e.getMessage(), e);
		
		//set error status code so JQuery AJAX can pick up the response as error
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		ModelAndView model = new ModelAndView("error-page");
		model.getModelMap().addAttribute("errorMessage", e.getMessage());
		
		return model;
		
	}

}
