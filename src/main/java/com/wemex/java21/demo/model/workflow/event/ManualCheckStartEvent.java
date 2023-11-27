package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManualCheckStartEvent(
        UUID id,
        UUID partyId,
        LocalDateTime startTime,
        String userId
) implements WorkflowEvent {}
