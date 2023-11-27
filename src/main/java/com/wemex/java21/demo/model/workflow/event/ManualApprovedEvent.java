package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManualApprovedEvent(
        UUID id,
        UUID partyId,
        LocalDateTime approveTime,
        String userId
) implements WorkflowEvent {}
