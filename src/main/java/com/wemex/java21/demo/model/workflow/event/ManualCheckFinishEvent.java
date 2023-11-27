package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManualCheckFinishEvent(
        UUID id,
        UUID partyId,
        LocalDateTime finishTime,
        String userId
) implements WorkflowEvent {}
