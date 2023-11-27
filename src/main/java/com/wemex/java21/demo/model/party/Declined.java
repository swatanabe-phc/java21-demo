package com.wemex.java21.demo.model.party;

import java.util.UUID;

public final class Declined extends Party {

    public Declined(UUID id, String partyName, String representativeName, String address) {
        super(id, partyName, representativeName, address);
    }

    @Override
    public String getState() {
        return "DECLINED";
    }

}