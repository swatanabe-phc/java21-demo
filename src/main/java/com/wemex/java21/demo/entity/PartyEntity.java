package com.wemex.java21.demo.entity;

import java.util.UUID;

public record PartyEntity(
        UUID id,
        String name,
        String representativeName,
        String address,
        String state
) {}
