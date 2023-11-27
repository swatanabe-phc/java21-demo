package com.wemex.java21.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public record EditHistory(
        UUID id,
        UUID partyId,
        String partyName,
        String representativeName,
        String address,
        LocalDateTime savedTime,
        String userId
) {}
