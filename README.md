NOTES:

1. Context Root: /gochat
2. Container used: apache-tomcat-8.0.28

3. Preloaded Users for testing:
   username/password: john_smith/john_smith
   username/password: linda_mcbride/linda_mcbride

4. The Mock users have been initialized having database relational behavior such as friends list (Please see com.gochat.dao.UserDB.java)

5. Spring MVC with Spring Security + OAuth2 implementation

6. Native JEE 7 WebSocket implementation

LIMITATIONS AND UNFINISHED FEATURES:
1. Private messaging with a friend on the list (Supposed to be implemented using Spring MVC Server-Sent Event implementation SseEmitter)
2. Push notification for incoming private messages
3. Polishing of browser address bar URL value
4. Polishing of events behavior and CSS styles
5. Use of OAuth access token for websocket endpoint connection is poorly implemented. the token can not be verified with the token store since the token store is within spring context and the websocket endpoint is not. There are work arounds but needs more time for implementation. Spring MVC 4.2 has its own websocket implementation and would be the right configuration to use in conjunction with Spring MVC.
