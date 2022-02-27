package com.falser.cloud.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.falser.cloud.message.dao.MessageCyclePlanDao;
import com.falser.cloud.message.dto.MessageCyclePlanDTO;
import com.falser.cloud.message.entity.Message;
import com.falser.cloud.message.entity.MessageCyclePlan;
import com.falser.cloud.message.service.MessageCyclePlanService;
import com.falser.cloud.message.vo.MessageCyclePlanAddVO;
import com.falser.cloud.message.vo.MessageCyclePlanModifyVO;
import com.falser.cloud.message.vo.MessageCyclePlanQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 周期消息计划(MessageCyclePlan)表服务实现类
 *
 * @author falser
 * @since 2022-02-27 13:44:12
 */
@Service
public class MessageCyclePlanServiceImpl extends ServiceImpl<MessageCyclePlanDao, MessageCyclePlan> implements MessageCyclePlanService {

    @Override
    public void add(MessageCyclePlanAddVO addVO) {
        MessageCyclePlan cyclePlan = new MessageCyclePlan();
        BeanUtils.copyProperties(addVO, cyclePlan);
        save(cyclePlan);
    }

    @Override
    public IPage<MessageCyclePlanDTO> getPageData(Page<MessageCyclePlan> page, MessageCyclePlanQueryVO queryVO) {
        LambdaQueryWrapper<MessageCyclePlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isEmpty(queryVO.getName()), MessageCyclePlan::getName, queryVO.getName());
        queryWrapper.eq(Objects.isNull(queryVO.getTimeCycle()), MessageCyclePlan::getTimeCycle, queryVO.getTimeCycle());
        queryWrapper.eq(Objects.isNull(queryVO.getType()), MessageCyclePlan::getType, queryVO.getType());
        Page<MessageCyclePlan> cyclePlanPage = page(page, queryWrapper);
        List<MessageCyclePlan> planList = cyclePlanPage.getRecords();
        List<MessageCyclePlanDTO> planDTOS = planList.stream().map(cyclePlan -> {
            MessageCyclePlanDTO planDTO = new MessageCyclePlanDTO();
            BeanUtils.copyProperties(cyclePlan, planDTO);
            return planDTO;
        }).collect(Collectors.toList());
        IPage<MessageCyclePlanDTO> dtoPage = new Page<>();
        dtoPage.setPages(cyclePlanPage.getPages());
        dtoPage.setCurrent(cyclePlanPage.getCurrent());
        dtoPage.setSize(cyclePlanPage.getSize());
        dtoPage.setTotal(cyclePlanPage.getTotal());
        dtoPage.setRecords(planDTOS);
        return dtoPage;
    }

    @Override
    public void modify(MessageCyclePlanModifyVO modifyVO) {
        MessageCyclePlan cyclePlan = new MessageCyclePlan();
        BeanUtils.copyProperties(modifyVO, cyclePlan);
        updateById(cyclePlan);
    }
}

