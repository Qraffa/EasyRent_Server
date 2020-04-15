package com.qraffa.easyrentboot.handler.websocket;

import com.qraffa.easyrentboot.util.SocketSessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * websocket建立连接事件
 * @date 2020/4/10
 */
@Component
public class SessionConnectEventListener extends BaseSessionEventListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        using(event,(user,session) -> {
            //如果当前用户没有登录（没有认证信息），就添加到游客里面
            if (user == null || "".equals(user) || "undefined".equals(user) || "null".equals(user)) {
                log.info("user is null");
            }
            log.info("{}<===>{},connect",user,session);
            SocketSessionRegistry.registerSessionId(user,session);
        });
    }
}