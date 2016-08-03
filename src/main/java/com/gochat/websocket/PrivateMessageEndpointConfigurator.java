package com.gochat.websocket;

import javax.websocket.server.ServerEndpointConfig;

/**
 * @author mpcariaso
 *
 */
public class PrivateMessageEndpointConfigurator extends ServerEndpointConfig.Configurator {

	
	private static PrivateMessageEndpoint privateMessageEndpoint;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		// TODO Auto-generated method stub
		
		if(privateMessageEndpoint == null) {
			privateMessageEndpoint = new PrivateMessageEndpoint();
		}
		
		if (PrivateMessageEndpoint.class.equals(endpointClass)) {
            return (T) privateMessageEndpoint;
        } else {
            throw new InstantiationException();
        }
		
		
		//return super.getEndpointInstance(endpointClass);
	}
}
