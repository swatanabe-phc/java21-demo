package com.wemex.java21.demo.model.party;

import java.util.UUID;

public final class PartyApproved extends Party {

    public PartyApproved(UUID id, String partyName, String representativeName, String address) {
        super(id, partyName, representativeName, address);
    }

    @Override
    public String getState() {
        return "APPROVED";
    }

}
