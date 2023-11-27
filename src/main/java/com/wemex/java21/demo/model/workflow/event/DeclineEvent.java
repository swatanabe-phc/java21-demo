package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeclineEvent(
        UUID id,
        UUID partyId,
        LocalDateTime declinedTime,
        String userId,
        String reason
) implements WorkflowEvent {}
