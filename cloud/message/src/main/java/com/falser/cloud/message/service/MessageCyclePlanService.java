package com.falser.cloud.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.falser.cloud.message.dto.MessageCyclePlanDTO;
import com.falser.cloud.message.entity.MessageCyclePlan;
import com.falser.cloud.message.vo.MessageCyclePlanAddVO;
import com.falser.cloud.message.vo.MessageCyclePlanModifyVO;
import com.falser.cloud.message.vo.MessageCyclePlanQueryVO;

/**
 * 周期消息计划(MessageCyclePlan)表服务接口
 *
 * @author falser
 * @since 2022-02-27 13:44:12
 */
public interface MessageCyclePlanService extends IService<MessageCyclePlan> {

    void add(MessageCyclePlanAddVO addVO);

    IPage<MessageCyclePlanDTO> getPageData(Page<MessageCyclePlan> page, MessageCyclePlanQueryVO queryVO);

    void modify(MessageCyclePlanModifyVO modifyVO);
}

