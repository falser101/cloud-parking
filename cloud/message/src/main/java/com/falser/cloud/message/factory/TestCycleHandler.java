package com.falser.cloud.message.factory;

import com.alibaba.fastjson.JSON;
import com.falser.cloud.message.dto.MessageCycleCreateDTO;
import com.falser.cloud.message.dto.MessageCycleResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestCycleHandler implements MessageCycleHandler{

    @Override
    public MessageCycleResultDTO handle(MessageCycleCreateDTO createDTO) {
        log.info("createDto: {}", JSON.toJSONString(createDTO));
        MessageCycleResultDTO resultDTO = new MessageCycleResultDTO();
        resultDTO.setUserId(createDTO.getUserId());
        resultDTO.setTitle(createDTO.getTitle());
        resultDTO.setContent(createDTO.getContent());
        return resultDTO;
    }
}
