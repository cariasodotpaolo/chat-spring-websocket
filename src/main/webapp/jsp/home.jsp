<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GoChat</title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/gochat.css" rel="stylesheet">
    <link href="css/home-template.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/chat-channel.js"></script>
    <script type="text/javascript" src="js/private-msg.js"></script>
    <script type="text/javascript">
        
    	function enterChannel(channel,display) {
    		/*
    		 * use AJAX to call in order to send custom headers
    		*/
    		$.ajax
    		({
    		  type: "GET",
    		  url: "<%=request.getContextPath()%>/chat/channel/" + channel,
    		  dataType: 'html',
    		  async: false,
    		  headers: {
    		    "Authorization": "Bearer ${accessToken}"
    		  },
    		  data: "channelDisplay=" + display,
    		  success: function (data){
    			     			  
    			  var w = window.open('about:blank', '_blank',"width=700,height=600,toolbar=0");
    			    w.document.write(data);
    			    w.document.close();
    		  }
    		});	
    	}
    	
    	function logout() {
    		$.ajax
    		({
    		  type: "GET",
    		  url: "<%=request.getContextPath()%>/chat/logout/",
    		  dataType: 'html',
    		  async: false,
    		  headers: {
    		    "Authorization": "Bearer ${accessToken}"
    		  },
    		  data: "userName=${user.userName}",
    		  success: function (data){
    			  
    			  var url =  "http://" + document.location.host + "<%=request.getContextPath()%>/chat/";
    			  
    			  var w = window.open(url, '_self',"");
    			    w.document.write(data);
    			    w.document.close();
    		  }
    		});	
    	}
    	
    </script>
</head>
<body>
	<div id="page-wrapper">

        <div id="header-wrapper">
            <h1><span class="app-name">GoChat</span></h1>
            <div style="float:left">
	        	<span class="user-online">Welcome, ${chatUser.fullName}!</span><br>
	        	<a href="javascript:void(0);" onclick="logout();" target="_self">Logout</a>
	        </div>
        </div>
        
        <div id="main-content-wrapper">
            <div id="left-content-wrapper">
                <div class="content-title-wrapper">
                    <span class="content-title">Friends</span>
                </div>
                <div id="left-content">
                    <c:forEach items="${chatList}" var="user">                    	
                    	<c:choose>
                    	<c:when test="${user.online}">
                    		<a href=""><span class="user-online">${user.fullName}</span></a>
                    	</c:when>
                    	<c:otherwise>
                    		<br>
                    		<span class="user-offline"><c:out value="${user.fullName} (offline)"></c:out></span>
                    	</c:otherwise>
                    	</c:choose>
                    </c:forEach>
                </div>
            </div>

            <div id="center-content-wrapper">
                    <div class="content-title-wrapper">
                        <span class="content-title">Message Board</span>
                    </div>
                    <div id="center-content">
                        
                    </div>
            </div>

            <div id="right-content-wrapper">
                <div class="content-title-wrapper">
                    <span class="content-title">Chat Rooms</span>
                </div>
                <div id="right-content">
						<c:forEach items="${channels}" var="channel">
                    		<p><a onclick="enterChannel('${channel.name}','${channel.displayName}');" href="javascript:void(0);" target="_blank"><span class="user-online" >${channel.displayName}</span></a></p>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div id="footer-wrapper">
            
        </div>
    </div>
</body>
</html>