package com.qraffa.easyrentboot.controller;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.qraffa.easyrentboot.model.ReturnModel;
import com.qraffa.easyrentboot.model.StatusEnum;
import com.qraffa.easyrentboot.model.entity.Message;
import com.qraffa.easyrentboot.model.entity.User;
import com.qraffa.easyrentboot.model.entity.websocket.InMessage;
import com.qraffa.easyrentboot.model.entity.websocket.OutMessage;
import com.qraffa.easyrentboot.model.exception.ExceptionModel;
import com.qraffa.easyrentboot.model.req.message.GetMessageListReq;
import com.qraffa.easyrentboot.model.res.message.GetMessageListRes;
import com.qraffa.easyrentboot.service.MessageService;
import com.qraffa.easyrentboot.util.SocketSessionRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "消息接口")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询消息列表
     * @param req
     * @param bindingResult
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/message")
    @ApiOperation(value = "查询消息列表")
    public ReturnModel getMessageList(@Validated GetMessageListReq req, BindingResult bindingResult) throws ExceptionModel {
        // 数据校验
        if(bindingResult.hasErrors()){
            throw new ExceptionModel(StatusEnum.INFORMATION_INCOMPLETE);
        }
        // 获取session
        //SessionUser sessionUser = (SessionUser)httpSession.getAttribute("user_session_key");
        // 获取消息列表
        List<Message> messageList=messageService.getMessage(Integer.valueOf(jwtUtil.getUserId()),req.getReceiverId(),1);
        int listSize=messageList.size();
        // 返回列表
        List<GetMessageListRes> getMessageResList = new ArrayList<>();
        // 发起请求的用户id
        Integer queryId = Integer.valueOf(jwtUtil.getUserId());
        for(int i=0;i<listSize;++i){
            GetMessageListRes getMessage=new GetMessageListRes();
            BeanUtils.copyProperties(messageList.get(i),getMessage);
            // 如果发起请求的用户id与消息中的发送者id相同,则置为true
            if(getMessage.getSenderId()==queryId) {
                getMessage.setIsSender(true);
            } else {
                getMessage.setIsSender(false);
            }
            getMessageResList.add(getMessage);
        }
        return new ReturnModel().withOkData(getMessageResList);
    }

    /**
     * 查询消息好友列表
     * @return
     * @throws ExceptionModel
     */
    @GetMapping("/messageUser")
    @ApiOperation(value = "查询消息好友列表")
    public ReturnModel getMessageUserList() throws ExceptionModel {
        Integer uid = Integer.valueOf(jwtUtil.getUserId());
        List<User> list = messageService.getMessageUserList(uid);
        return new ReturnModel().withOkData(list);
    }

    /**
     * 广播推送
     * @param inMessage 接受消息体
     */
    @MessageMapping("/chat")
    public void singleChat(InMessage inMessage) throws ExceptionModel {
        String toUser = inMessage.getTo();
        List<Message> messageList = messageService.handlerMessage(inMessage);
        if(messageList != null) {
            // 消息推送给订阅了 /queue/chat/{toUser} 的用户
            messagingTemplate.convertAndSend("/queue/chat/" + toUser, messageList);
        }
    }
}
