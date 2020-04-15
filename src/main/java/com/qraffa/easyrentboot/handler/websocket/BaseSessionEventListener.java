package com.qraffa.easyrentboot.handler.websocket;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 会话事件监听基类
 *
 * @author rxliuli
 */
public abstract class BaseSessionEventListener<Event extends AbstractSubProtocolEvent> implements ApplicationListener<Event> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 计算出 user id 和 session id 并传入到自定义的函数中
     *
     * @param event      事件
     * @param biConsumer 自定义的操作
     */
    protected void using(Event event, BiConsumer<String, String> biConsumer) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        List<String> shaNativeHeader = sha.getNativeHeader("token");
        String user;
        if (shaNativeHeader == null || shaNativeHeader.isEmpty()) {
            user = null;
        } else {
            user = shaNativeHeader.get(0);
        }
        String sessionId = sha.getSessionId();
        // 将token转换成用户uid
        String uid=jwtUtil.getUserId(user);
        log.info("token id 映射关系 {} <===> {}",user,uid);
        biConsumer.accept(uid, sessionId);
    }
}