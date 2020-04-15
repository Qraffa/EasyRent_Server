package com.qraffa.easyrentboot.service;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.dao.MessageDao;
import com.qraffa.easyrentboot.dao.UserDao;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Message;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.entity.websocket.InMessage;
import com.qraffa.easyrentboot.model.entity.websocket.OutMessage;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.util.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 检查用户是否存在
     * @param uid
     * @throws ExceptionModel
     */
    public void checkUserById(Integer uid) throws ExceptionModel {
        int isUser=userDao.checkUserById(uid);
        if(isUser==0){
            // 用户不存在
            throw new ExceptionModel(StatusEnum.USER_NONEXISTENT);
        }
    }

    /**
     * 查询消息列表
     * @param uid
     * @param receiverId
     * @param status
     * @return
     * @throws ExceptionModel
     */
    public List<Message> getMessage(Integer uid,Integer receiverId,Integer status) throws ExceptionModel {
        // 检查用户是否存在
        //checkUserById(uid);
        checkUserById(receiverId);
        // 查询消息列表
        List<Message> messageList=messageDao.queryMessageById(uid,receiverId,status);
        Collections.reverse(messageList);
        return messageList;
    }

    /**
     * 添加消息
     * @param message
     * @throws ExceptionModel
     */
    public void insertMessage(Message message) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(message.getSenderId());
        checkUserById(message.getReceiverId());
        // 添加消息
        messageDao.insertMessage(message);
    }

    /**
     * 更新消息状态
     * @param receiverId
     * @throws ExceptionModel
     */
    public void updateMessage(Integer receiverId) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(receiverId);
        // 更新消息状态
        messageDao.updateMessage(receiverId);
    }

    /**
     * 处理消息
     * @param inMessage
     * @return
     */
    public List<Message> handlerMessage(InMessage inMessage) throws ExceptionModel {
        // 通过token转换成用户id
        Integer fromId=Integer.valueOf(jwtUtil.getUserId(inMessage.getToken()));
        // 查询发送方用户信息
        User fromUser = userDao.queryUserById(fromId);
        // 查询接受方用户信息
        User toUser = userDao.queryUserByName(inMessage.getTo());
        // 用户不存在
        if(fromUser == null || toUser == null) {
            throw new ExceptionModel(StatusEnum.USER_NONEXISTENT);
        }
        // 根据用户id获取用户的在线情况
        String toPath = SocketSessionRegistry.getSessionId(String.valueOf(toUser.getUid()));
        // 消息类
        Message message=new Message();
        message.setSenderId(fromUser.getUid());
        message.setReceiverId(toUser.getUid());
        message.setContent(inMessage.getContent());
        Long time = new Date().getTime();
        message.setSendTime(time);

        List<Message> messageList=null;
        if(toPath == null) {
            // 接收方用户不在线
            message.setStatus(0);
        } else {
            message.setStatus(1);
            messageList=new ArrayList<>();
            messageList.add(message);
        }
        // 添加消息进数据库
        messageDao.insertMessage(message);
        return messageList;
    }

    /**
     * 查询消息好友列表
     * @param uid
     * @return
     * @throws ExceptionModel
     */
    public List<User> getMessageUserList(Integer uid) throws ExceptionModel {
        // 检查用户是否存在
        checkUserById(uid);
        // 获取好友列表
        return messageDao.getMessageUserList(uid);
    }

}
