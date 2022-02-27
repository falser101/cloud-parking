package com.falser.cloud.message.controller;


import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.message.entity.MessageCyclePlan;
import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.mq.MessageCycleMqProducer;
import com.falser.cloud.message.service.MessageService;
import com.falser.cloud.message.vo.MessageCycleVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息数据表(Message)表控制层
 *
 * @author falser
 * @since 2022-02-26 11:33:44
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    /**
     * 服务对象
     */
    private final MessageService messageService;

    private final MessageCycleMqProducer producer;

    public MessageController(MessageService messageService, MessageCycleMqProducer producer) {
        this.messageService = messageService;
        this.producer = producer;
    }

    @PostMapping
    private void test(@RequestBody MessageCycleVO vo){
        MessageCyclePlan plan = new MessageCyclePlan();
        plan.setId(1L);
        plan.setName("测试");
        plan.setTitle("测试标题");
        plan.setContent("测试内容");
        plan.setType(vo.getMessageCycleEnum());
        producer.sendMsg(plan);
    }

    @PostMapping
    public ApiResponse add(@RequestBody MessageCycleVO vo){
        return ApiResponse.ofSuccess();
    }

    @GetMapping
    public ApiResponse list(){
        return ApiResponse.ofSuccess();
    }

    @GetMapping("{id}")
    public ApiResponse detail(@PathVariable Long id){
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping("{id}")
    public ApiResponse delete(@PathVariable Long id){
        return ApiResponse.ofSuccess();
    }

    @PutMapping
    public ApiResponse update(){
        return ApiResponse.ofSuccess();
    }
}

