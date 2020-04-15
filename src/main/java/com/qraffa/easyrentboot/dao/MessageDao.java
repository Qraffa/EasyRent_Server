package com.qraffa.easyrentboot.dao;

import com.qraffa.easyrentboot.model.entity.Message;
import com.qraffa.easyrentboot.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息接口
 */
@Mapper
@Repository
public interface MessageDao {

    /***
     * 根据uid和接受者id查询消息
     * @param receiverId
     * @return
     */
    List<Message> queryMessageById(@Param("uid") Integer uid, @Param("receiverId") Integer receiverId, @Param("status") Integer status);

    /***
     * 发送消息
     * @param message
     * @return
     */
    int insertMessage(Message message);

    /**
     * 修改消息接受状态
     * @param receiverId
     */
    void updateMessage(@Param("receiverId") Integer receiverId);

    /**
     * 根据用户id查询好友列表
     * @param uid
     * @return
     */
    List<User> getMessageUserList(@Param("uid") Integer uid);

}
