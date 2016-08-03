/**
 * 
 */
package com.gochat.messaging;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author mpcariaso
 *
 */
public class MessageEncoder implements Encoder.Text<Message> {
	
	@Override
	public String encode(final Message message) throws EncodeException {
		return Json.createObjectBuilder()
				   .add("message", message.getMessage())
				   .add("sender", message.getSender())
				   .add("recipient", message.getRecipient())
				   .add("token", message.getToken())
				   .add("timestamp", message.getTimestamp().toString()).build()
				   .toString();
	}
	
	@Override
	public void init(final EndpointConfig config) {
	}
	
	@Override
	public void destroy() {
	}
}
