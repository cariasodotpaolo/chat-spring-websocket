package com.gochat.websocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gochat.messaging.Message;
import com.gochat.messaging.MessageDecoder;
import com.gochat.messaging.MessageEncoder;
import com.gochat.websocket.PrivateMessageEndpointConfigurator;

/**
 * @author mpcariaso
 *
 */
@ServerEndpoint(value = "/endpoint/private/{sender}/{recipient}/{token}", 
				encoders = MessageEncoder.class, 
				decoders = MessageDecoder.class,
				configurator = PrivateMessageEndpointConfigurator.class)
public class PrivateMessageEndpoint {
	
	private final Logger logger = LoggerFactory.getLogger(PrivateMessageEndpoint.class);
	

	/*
	 * create a separate cache/repository of sessions because session.getOpenSessons()
	 * do not contains itself and not all open sessions.
	 */
	private static List<Session> privateSessions;
	
	@OnOpen
	public void open(final Session session,
					 @PathParam("sender") final String sender,
					 @PathParam("recipient") final String recipient,
					 @PathParam("token") final String token) {
		
		logger.debug("Private message session opened for recipient user: " + recipient);
		
		if(privateSessions == null) {
			privateSessions = new ArrayList<>();
		}
				
		session.getUserProperties().put("recipient", recipient);
		session.getUserProperties().put("sender", sender);
		session.getUserProperties().put("token", token);
		
		privateSessions.add(session);
	}
 
	@OnMessage
	public void onMessage(final Session session, 
						  final Message message) {
		
		String sessionToken = (String) session.getUserProperties().get("token");
		String sessionSender = (String) session.getUserProperties().get("sender");
		String sessionRecipient = (String) session.getUserProperties().get("recipient");
		
		try {
			
			if (sessionToken == null ) {
				throw new Exception("Unauthorized session.");
			} else if(!sessionToken.equals(message.getToken())) {
				throw new Exception("Invalid session.");
			}		
		
			for (Session openSession : session.getOpenSessions()) {
				if (openSession.isOpen() && 
					sessionRecipient.equals(message.getRecipient()) &&
					sessionSender.equals(message.getSender())) {
					openSession.getBasicRemote().sendObject(message);
					break;
				}
			}
		} catch (EncodeException ee) {
			logger.error(ee.getMessage(), ee);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@OnClose
	public void close(final Session session) {
		
		String sessionSender = (String) session.getUserProperties().get("sender");
		String sessionRecipient = (String) session.getUserProperties().get("recipient");
		String sessionToken = (String) session.getUserProperties().get("token");
		
		for(int i = 0; i < privateSessions.size(); i++) {
			Session s = privateSessions.get(i);
			String sSender = (String) s.getUserProperties().get("sender");
			String sRecipient = (String) s.getUserProperties().get("recipient");
			String sToken = (String) s.getUserProperties().get("token");
			
			if (sessionSender.equals(sSender) && sessionRecipient.equals(sRecipient) && sessionToken.equals(sToken)) {
				privateSessions.remove(i);
				break;
			}
		}
	}
}