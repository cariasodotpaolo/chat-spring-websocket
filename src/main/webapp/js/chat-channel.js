	var wsocket;
	var serviceLocation = "ws://" + document.location.host + "/gochat/channel/";
	

	function onMessageReceived(event) {
		
		var msg = JSON.parse(event.data); // native API
		
		var $messageLine = $('<tr><td class="received">' + msg.timestamp +
							 '</td><td class="user label label-info">' + msg.sender +
							 '</td><td class="message badge">' + msg.message +
							 '</td></tr>');
		
		$chatWindow.append($messageLine);
	}
	function sendMessage(message) {		
		wsocket.send(message);
		$message.val('').focus();		
	}

	function connectToServer(channel,token) {		
		wsocket = new WebSocket(serviceLocation + channel + "/" + token);
		wsocket.onmessage = onMessageReceived;
	}

	function leaveChannel() {
		wsocket.close();		
	}