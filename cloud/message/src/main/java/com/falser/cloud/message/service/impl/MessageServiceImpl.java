package com.falser.cloud.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.message.dao.MessageDao;
import com.falser.cloud.message.entity.Message;
import com.falser.cloud.message.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * 消息数据表(Message)表服务实现类
 *
 * @author falser
 * @since 2022-02-26 11:33:44
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

}

