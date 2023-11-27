package com.wemex.java21.demo.model.party;

import java.util.UUID;

public final class PartyCreated extends Party {

    public PartyCreated() {
        // idはUUIDで生成する
        super(UUID.randomUUID(), null, null, null);
    }


    @Override
    public String getState() {
        return "CREATED";
    }
}
