package com.falser.cloud.message.factory;

import com.falser.cloud.message.dto.MessageCycleCreateDTO;
import com.falser.cloud.message.dto.MessageCycleResultDTO;
import com.falser.cloud.message.entity.Message;
import com.falser.cloud.message.enums.MessageCycleEnum;
import com.falser.cloud.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class MessageClient {

    private final Map<MessageCycleEnum, Function<MessageCycleCreateDTO, MessageCycleResultDTO>>
            cycleHandlerMap = new HashMap<>();

    private final TestCycleHandler testCycleHandler;
    private final NormalCycleHandler normalCycleHandler;
    private final MessageService messageService;

    public MessageClient(TestCycleHandler testCycleHandler,
                         NormalCycleHandler normalCycleHandler,
                         MessageService messageService) {
        this.testCycleHandler = testCycleHandler;
        this.normalCycleHandler = normalCycleHandler;
        this.messageService = messageService;
    }

    @PostConstruct
    private void init() {
        cycleHandlerMap.put(MessageCycleEnum.TEST, testCycleHandler::handle);
        cycleHandlerMap.put(MessageCycleEnum.NORMAL, normalCycleHandler::handle);
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleCycleMessage(MessageCycleCreateDTO createDTO) {
        // 1.查询要发送消息的人
        List<Long> userIdList = getUserIdList(createDTO.getPlanId());
        // 2.每个人单独处理
        userIdList.forEach(userId -> handle(createDTO, userId));
    }

    private void handle(MessageCycleCreateDTO createDTO, Long userId) {
        // 3.组装消息
        createDTO.setUserId(userId);
        Function<MessageCycleCreateDTO, MessageCycleResultDTO> function = cycleHandlerMap.get(createDTO.getType());
        MessageCycleResultDTO resultDTO = function.apply(createDTO);
        log.info("resultDTO: {}", resultDTO);
        // 4.入库
        Message message = new Message();
        message.setContent(resultDTO.getContent());
        message.setTitle(resultDTO.getTitle());
        message.setUserId(resultDTO.getUserId());
        message.setCreateTime(LocalDateTime.now());
        message.setType(createDTO.getType().name());
        messageService.save(message);
    }

    private List<Long> getUserIdList(Long planId) {
        return List.of(1L, 2L, 3L, 4L, 5L, 6L);
    }


}
