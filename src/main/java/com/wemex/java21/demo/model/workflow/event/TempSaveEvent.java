package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record TempSaveEvent(
        UUID id,
        UUID partyId,
        String partyName,
        String representativeName,
        String address,
        LocalDateTime savedTime,
        String userId
) implements WorkflowEvent {
}
