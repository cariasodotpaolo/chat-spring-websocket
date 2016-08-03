package com.gochat.websocket;

import javax.annotation.Resource;
import javax.websocket.server.ServerEndpointConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gochat.service.TokenVerificationService;

/**
 * @author mpcariaso
 *
 * This configurator makes sure that the channel endpoint server instance is singleton
 * Default behavior is that each client is given its own endpoint instance
 */

//@Service
//@Scope("singleton")
public class ChannelEndpointConfigurator extends ServerEndpointConfig.Configurator {
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelEndpointConfigurator.class);
	
	//@Resource
	private ChatChannelEndpoint channelEndpoint;
		
	@Resource
	private TokenVerificationService tokenVerificationService;
		
	/*@PostConstruct
	public void init() {
		if(this.channelEndpoint != null) logger.info("ChatChannelEndpoint has been instantiated in Spring context.");
		if(this.tokenVerificationService != null) logger.info("TokenVerificationService has been instantiated in Spring context");
		else logger.error("TokenVerificationService instantiation FAILED.");
		
		if(this.channelEndpoint != null && this.tokenVerificationService != null) {
			this.channelEndpoint.setTokenVerificationService(this.tokenVerificationService);
		}
		
		tokenVerificationService = (TokenVerificationService) context.getBean("tokenVerificationService");
		if(tokenVerificationService != null) logger.info("TokenVerificationService has been instantiated.");
		try {
			getEndpointInstance(ChatChannelEndpoint.class);
		} catch (InstantiationException e) {
			logger.error("ChatChannelEndpoint websocket server instantiation failed.", e);
		}
	}*/
		
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		// TODO Auto-generated method stub
		
		if(channelEndpoint == null) {
			channelEndpoint = new ChatChannelEndpoint();
			//logger.debug("ChannelEndpointConfigurator.getEndpointInstance() called.");			
			//channelEndpoint.setTokenVerificationService(tokenVerificationService);
			//if(tokenVerificationService != null) logger.info("TokenVerificationService has been loaded to ChatChannelEndpoint instance.");
		}
		
		if (ChatChannelEndpoint.class.equals(endpointClass)) {
            return (T) this.channelEndpoint;
        } else {
            throw new InstantiationException();
        }
		
		
		//return super.getEndpointInstance(endpointClass);
	}

	
	
}
