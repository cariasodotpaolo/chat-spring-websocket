package com.gochat.websocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.gochat.messaging.Message;
import com.gochat.messaging.MessageDecoder;
import com.gochat.messaging.MessageEncoder;

/**
 * @author mpcariaso
 *
 */
@ServerEndpoint(value = "/private/{sender}/{recipient}", 
				encoders = MessageEncoder.class, 
				decoders = MessageDecoder.class,
				configurator = PrivateMessageEndpointConfigurator.class)
public class PrivateMessageEndpoint {
	
	private final Logger logger = LoggerFactory.getLogger(PrivateMessageEndpoint.class);
	

	/*
	 * create a separate cache/repository of sessions because session.getOpenSessons()
	 * do not contains itself and not all open sessions.
	 */
	private static List<Session> sessions;
	
	@OnOpen
	public void open(final Session session,
					 @PathParam("sender") final String sender,
					 @PathParam("recipient") final String recipient) {
		
		logger.debug("Private message session opened for recipient user: " + recipient);
		
		if(sessions == null) {
			sessions = new ArrayList<>();
		}
		
		session.getUserProperties().put("recipient", recipient);
		session.getUserProperties().put("sender", sender);
		
		sessions.add(session);
	}
 
	@OnMessage
	public void onMessage(final Session session, 
						  final Message message) {
		String recipientUser = (String) session.getUserProperties().get("recipientUser");
		try {
			for (Session openSession : session.getOpenSessions()) {
				if (openSession.isOpen() && recipientUser.equals(openSession.getUserProperties().get("recipientUser"))) {
					openSession.getBasicRemote().sendObject(message);
				}
			}
		} catch (EncodeException ee) {
			logger.error(ee.getMessage(), ee);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		}
	}
}
