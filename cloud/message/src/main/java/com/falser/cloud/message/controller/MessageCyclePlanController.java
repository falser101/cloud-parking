package com.falser.cloud.message.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.falser.cloud.common.web.ApiResponse;
import com.falser.cloud.message.entity.MessageCyclePlan;
import com.falser.cloud.message.service.MessageCyclePlanService;
import com.falser.cloud.message.vo.MessageCyclePlanAddVO;
import com.falser.cloud.message.vo.MessageCyclePlanModifyVO;
import com.falser.cloud.message.vo.MessageCyclePlanQueryVO;
import org.springframework.web.bind.annotation.*;

/**
 * 周期消息计划(MessageCyclePlan)表控制层
 *
 * @author falser
 * @since 2022-02-27 13:44:12
 */
@RestController
@RequestMapping("/api/message/cycle")
public class MessageCyclePlanController {
    /**
     * 服务对象
     */
    private final MessageCyclePlanService messageCyclePlanService;

    public MessageCyclePlanController(MessageCyclePlanService messageCyclePlanService) {
        this.messageCyclePlanService = messageCyclePlanService;
    }

    @PostMapping
    public ApiResponse<?> add(@RequestBody MessageCyclePlanAddVO addVO){
        messageCyclePlanService.add(addVO);
        return ApiResponse.ofSuccess();
    }

    @GetMapping
    public ApiResponse<?> list(Page<MessageCyclePlan> page, MessageCyclePlanQueryVO queryVO){
        return ApiResponse.ofSuccess(messageCyclePlanService.getPageData(page, queryVO));
    }

    @GetMapping("{id}")
    public ApiResponse<?> detail(@PathVariable Long id){
        return ApiResponse.ofSuccess(messageCyclePlanService.getById(id));
    }

    @DeleteMapping("{id}")
    public ApiResponse<?> delete(@PathVariable Long id){
        return ApiResponse.ofSuccess(messageCyclePlanService.removeById(id));
    }

    @PutMapping
    public ApiResponse<?> update(@RequestBody MessageCyclePlanModifyVO modifyVO){
        messageCyclePlanService.modify(modifyVO);
        return ApiResponse.ofSuccess();
    }
}

