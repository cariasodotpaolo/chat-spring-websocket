package com.gochat.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.gochat.domain.ChatChannel;

/**
 * @author mpcariaso
 *
 */
@Repository
@Scope("singleton")
public class ChannelCache {

	private Map<String,ChatChannel> channels;
	
	private long sequenceId;
	
	@PostConstruct
	public void initCache() {
		channels = new TreeMap<>();
		
		sequenceId = 1;
		
		channels.put("travel", new ChatChannel(sequenceId++, "travel", "Travel and Leisure"));
		channels.put("photography", new ChatChannel(sequenceId++, "photography", "Photography and Arts"));
		channels.put("technology", new ChatChannel(sequenceId++, "gadgets", "Technology and Gadgets"));
		channels.put("pets", new ChatChannel(sequenceId++, "pets", "Pet Lovers"));
		channels.put("health", new ChatChannel(sequenceId++, "health", "Health and Fitness"));		
	}
	
	public void addChannel(String key, ChatChannel channel) {
		
		channel.setId(++sequenceId);
		channels.put(key, channel);
	}
	
	public List<ChatChannel> getChannels() {
		
		List<ChatChannel> channelList = new ArrayList<>();
		
		for(Entry<String,ChatChannel> entry: channels.entrySet()) {
			channelList.add(entry.getValue());
		}
		
		return channelList;
	}
}
