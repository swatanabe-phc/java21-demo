package com.wemex.java21.demo.model.party;

import java.util.UUID;

public final class PartyRegistered extends Party {
    private final String remarks;

    public PartyRegistered(UUID id, String partyName, String representativeName, String address, String remarks) {
        super(id, partyName, representativeName, address);
        this.remarks = remarks;
    }

    public String getRemarks() {
        return this.remarks;
    }

    @Override
    public String getState() {
        return "REGISTERED";
    }
}
