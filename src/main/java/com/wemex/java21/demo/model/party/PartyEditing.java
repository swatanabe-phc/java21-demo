package com.wemex.java21.demo.model.party;

import java.util.UUID;

public final class PartyEditing extends Party {

    // 編集中状態で利用される状態変数
    private final int editCount;
    public PartyEditing(UUID partyId, String partyName, String representativeName, String address, int editCount) {
        super(partyId, partyName, representativeName, address);
        this.editCount = editCount;
    }

    public int getEditCount() {
        return this.editCount;
    }

    @Override
    public String getState() {
        return "EDITING";
    }
}
