package com.gochat.controller;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author mpcariaso
 *
 */
@Controller
@RequestMapping("/chat/notification")
public class NotificationController {

	private Map<Long,SseEmitter> sseEmitters = new Hashtable<>(1);
	
	
	@RequestMapping(value="/connect",method = RequestMethod.GET)
	public SseEmitter openConnection() {
		
		if(sseEmitters.isEmpty()) {
			sseEmitters.put(1L, new SseEmitter(99999L));
		}
		
		return sseEmitters.get(1L);
	}
}
