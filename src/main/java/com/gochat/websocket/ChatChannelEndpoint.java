package com.gochat.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gochat.messaging.Message;
import com.gochat.messaging.MessageDecoder;
import com.gochat.messaging.MessageEncoder;
import com.gochat.service.TokenVerificationService;

/**
 * @author mpcariaso
 *
 */
@ServerEndpoint(value = "/endpoint/channel/{channel}/{token}", 
				encoders = MessageEncoder.class, 
				decoders = MessageDecoder.class,
				configurator = ChannelEndpointConfigurator.class)
public class ChatChannelEndpoint {
	
	private final Logger logger = LoggerFactory.getLogger(ChatChannelEndpoint.class);
	
	/*
	 * create a separate cache/repository of sessions because session.getOpenSessons()
	 * do not contains itself and not all open sessions.
	 */
	private static List<Session> channelSessions;	
	
	/*private TokenVerificationService tokenVerificationService;
	
	@PostConstruct
	public void init() {
		if(tokenVerificationService == null) logger.debug("TokenVerificationService is NULL!.");
		else logger.debug("TokenVerificationService is NOT NULL!.");
		logger.debug("Endpoint hash: " + this.toString());
	}*/
 			
	@OnOpen
	public void open(final Session session, 
					 @PathParam("channel") final String channel,
					 @PathParam("token") final String token) {
		
		logger.info("session opened and bound to channel: " + channel);		
		logger.debug("Open Session ID: " + session.getId());
		logger.debug("Endpoint hash: " + this.toString());
		logger.debug("token param: " + token);
		
		if(channelSessions == null) {
			channelSessions = new ArrayList<>();
		}
		
		if(token != null && !token.isEmpty()) {
			session.getUserProperties().put("channel", channel);			
			session.getUserProperties().put("token", token);
			channelSessions.add(session);
		}
	}
 
	@OnMessage
	public void onMessage(final Session session, final Message message) {
				
		String channel = (String) session.getUserProperties().get("channel");
		String token = (String) session.getUserProperties().get("token");
		
		
		logger.debug("Message from " + message.getSender() + ": " + message.getMessage() + " to Channel: " + channel);
		
		//message.setMessage(message.getMessage() + "(From Server)");
		
		try {
			
			if (token == null ) {
				throw new Exception("Unauthorized session.");
			} else if(!token.equals(message.getToken())) {
				throw new Exception("Invalid session.");
			} 
			
			if (message.getToken() == null || message.getToken().isEmpty()) throw new Exception("Missing Token.");
			else if (!message.getToken().equals(token)) throw new Exception("Invalid Token.");
			
			for (Session openSession : channelSessions) {
				
				String openSessionChannel = (String) openSession.getUserProperties().get("channel");
				
				if (openSession.isOpen() && channel.equals(openSessionChannel) ) {
					
					logger.debug("Sending message to Session: " + openSession.getId());
					openSession.getBasicRemote().sendObject(message);
				}
			}
		} catch (IOException | EncodeException e) {
			logger.error("Server onMessage failed.", e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@OnClose
	public void close(final Session session) {
		
		String sessionChannel = (String) session.getUserProperties().get("channel");
		String sessionToken = (String) session.getUserProperties().get("token");
		
		for(int i = 0; i < channelSessions.size(); i++) {
			Session s = channelSessions.get(i);
			String sChannel = (String) s.getUserProperties().get("channel");
			String sToken = (String) s.getUserProperties().get("token");
			
			if (sessionChannel.equals(sChannel) && sessionToken.equals(sToken)) {
				channelSessions.remove(i);
				break;
			}
		}
	}
	
	

	/*public void setTokenVerificationService(TokenVerificationService tokenVerificationService) {
		this.tokenVerificationService = tokenVerificationService;
	}*/

}
