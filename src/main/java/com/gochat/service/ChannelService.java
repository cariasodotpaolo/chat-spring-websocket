package com.gochat.service;

import java.util.List;

import com.gochat.domain.ChatChannel;

/**
 * @author mpcariaso
 *
 */
public interface ChannelService {

	public List<ChatChannel> getChannels();

}