<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>GoChat</title>
	<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/gochat.css" rel="stylesheet">
</head>
<body>
		<div class="container chat-signin">
        	
        	<p/>
            <form class="form-signin" action="<%=request.getContextPath()%>/login" method="post">
            	<div align="center"><h2 class="form-signin-heading"><span class="app-name">GoChat</span></h2></div>
                <table>
                    <tr>
                        <td>User Name</td>
                        <td><input class="input-block-level" type="text" name="userName" id="userName"/></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input class="input-block-level" type="password" name="password" id="password"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="checkbox" name="keepSignedIn" value="yes"/>Keep me signed in.</td>
                    </tr>                    
                    <tr>
                        <td></td>
                        <td><input class="btn btn-large btn-block btn-primary" type="submit" value="Sign In"/></td>
                    </tr>
                    
                    <c:if test="${not empty errorMessage}">
                      <tr>
                        <td></td>
                        <td><span class="error-message">${errorMessage}</span></td>
                      </tr>
                    </c:if>                   
                </table>                
            </form>
        </div>
</body>
</html>