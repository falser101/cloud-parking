package com.falser.cloud.message.factory;

import com.falser.cloud.message.dto.MessageCycleCreateDTO;
import com.falser.cloud.message.dto.MessageCycleResultDTO;

public interface MessageCycleHandler {
    MessageCycleResultDTO handle(MessageCycleCreateDTO createDTO);
}
