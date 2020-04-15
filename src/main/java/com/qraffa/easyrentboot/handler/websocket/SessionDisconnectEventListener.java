package com.qraffa.easyrentboot.handler.websocket;

import com.qraffa.easyrentboot.util.SocketSessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * websocket断开连接事件
 * @date 2020/4/10
 */
@Component
public class SessionDisconnectEventListener extends BaseSessionEventListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        using(event,(user,session) -> {
            log.info("{}<===>{},disconnect",user,session);
            SocketSessionRegistry.removeSessionId(session);
        });
    }
}