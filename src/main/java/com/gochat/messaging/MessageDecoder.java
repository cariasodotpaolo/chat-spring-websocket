package com.gochat.messaging;

import java.io.StringReader;
import java.util.Date;
 
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author mpcariaso
 *
 */
public class MessageDecoder implements Decoder.Text<Message> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	public Message decode(final String payload) throws DecodeException {
		
		Message message = new Message();
		
		JsonObject jsonObject = Json.createReader(new StringReader(payload)).readObject();
		message.setMessage(jsonObject.getString("message"));
		message.setSender(jsonObject.getString("sender"));
		message.setToken(jsonObject.getString("token"));
		message.setRecipient(jsonObject.getString("recipient"));
		message.setTimestamp(new Date());
		
		return message;
	}
 
	@Override
	public boolean willDecode(final String payload) {
		return true;
	}
}
