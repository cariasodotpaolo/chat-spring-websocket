<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>${channelDisplay}</title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/gochat.css" rel="stylesheet">
    <link href="css/home-template.css" rel="stylesheet">    
    <script type="text/javascript" src="js/chat-channel.js"></script>
    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    
    <script type="text/javascript">    
	    
		var $message;
		var $chatWindow;
		var $mainChatWindow;
		var channel = '${channel}';
		var token = '${accessToken}';
	
	    $( document ).ready(function() {	    		    	    	
	    	
			$message = $('#message');
			$chatWindow = $('#response');
			$mainChatWindow = $('#main-chat-wrapper');
			
			connectToSecureServer(channel,token);
			
			$message.focus();
						
			$('#chat-form').submit(function(event) {
				
				//Prevent submitting the inherited URL/address
				event.preventDefault();				
				var msg = '{"message":"' + $message.val() + '", "sender":"${chatUserFullName}", "recipient":"", "token":"${accessToken}", "timestamp":""}';
				sendMessage(msg);
			});
			
			$('#send-message').click(function(event) {
				event.preventDefault();				
				var msg = '{"message":"' + $message.val() + '", "sender":"${chatUserFullName}", "recipient":"", "token":"${accessToken}","timestamp":""}';				
				sendMessage(msg);
			});
			
			$('#leave-channel').click(function(){				
				leaveChannel();
				$mainChatWindow.empty();
			});
	    });
	    
    </script>
    
</head>
<body>
	<div id = "main-chat-wrapper" class="container chat-wrapper">
		<form id="chat-form">
			<h3 class="alert alert-success">${channelDisplay}</h3>
			<table id="response" class="table table-bordered"></table>
			<fieldset>
				<legend>Enter your message..</legend>
				<div class="controls">
					<input type="text" class="input-block-level" placeholder="Your message..." id="message" style="height:60px"/>
					<input type="button" id="send-message" class="btn btn-large btn-block btn-primary" value="Send message"/>
					<button class="btn btn-large btn-block" type="button" id="leave-channel">Leave	Channel</button>
				</div>
			</fieldset>
		</form>
	</div>
</body>
</html>