package com.wemex.java21.demo.model.workflow.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterEvent(
        UUID id,
        UUID partyId,
        String partyName,
        String representativeName,
        String address,
        LocalDateTime registerTime,
        String userId,
        // 申請時に備考が設定できる
        String remarks
) implements WorkflowEvent {
}
