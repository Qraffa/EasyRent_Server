package com.qraffa.easyrentboot.handler.websocket;

import com.qraffa.easyrentboot.model.entity.Message;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.service.MessageService;
import com.qraffa.easyrentboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

/**
 * websocket监听订阅事件
 */
@Component
public class SessionSubscribeEventListener extends BaseSessionEventListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        using(event,(user,session) -> {
            log.info("{}<===>{},subscribe",user,session);
            List<Message> messageList=null;
            try {
                // 获取离线消息
                messageList=messageService.getMessage(null,Integer.valueOf(user),0);
                // 将离线消息状态修改为在线状态
                messageService.updateMessage(Integer.valueOf(user));
                // 根据用户id获取用户信息
                User toUser = userService.getUser(Integer.valueOf(user));
                messagingTemplate.convertAndSend(
                        "/queue/chat/"+toUser.getUname(),
                        messageList
                );
            } catch (ExceptionModel exceptionModel) {
                exceptionModel.printStackTrace();
            }
        });
    }
}
