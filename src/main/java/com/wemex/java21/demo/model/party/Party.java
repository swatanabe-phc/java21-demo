package com.wemex.java21.demo.model.party;

import com.wemex.java21.demo.model.workflow.event.RegisterEvent;
import com.wemex.java21.demo.model.workflow.event.TempSaveEvent;

import java.util.UUID;

public sealed abstract class Party permits PartyApproved,
        PartyRegistered,
        PartyCreated,
        PartyEditing,
        ManualChecking,
        ManualChecked,
        Declined{

    final UUID id;
    final String name;
    final String representativeName;
    final String address;

    Party(UUID id, String partyName, String representativeName, String address) {
        this.id = id;
        this.name = partyName;
        this.representativeName = representativeName;
        this.address = address;
    }

    abstract public String getState();

    /**
     * tempSaveアクションはPartyCreatedもしくはEditingからのみ実行可能
     */
    // tempSaveアクション
    public Party tempSave(TempSaveEvent event) {
        return switch (this) {
            // PartyCreatedは作られたばかりなのでeditCountは1
            case PartyCreated partyCreated ->
                    new PartyEditing(partyCreated.id, event.partyName(), event.representativeName(), event.address(), 1);
            // PartyEditingは編集されているのでeditCountをインクリメント
            case PartyEditing partyEditing -> {
                int editCount = partyEditing.getEditCount() + 1;
                yield new PartyEditing(partyEditing.id, event.partyName(), event.representativeName(), event.address(), editCount);
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    /**
     * instanceOfを使った従前の書き方
     */
    // tempSaveアクション
    public Party tempSaveOldStyle(TempSaveEvent event) {
        if(this instanceof PartyCreated) {
            return new PartyEditing(this.id, event.partyName(), event.representativeName(), event.address(), 1);
        } else if (this instanceof PartyEditing) {
            PartyEditing partyEditing = (PartyEditing) this;
            int editCount = partyEditing.getEditCount() + 1;
            return new PartyEditing(partyEditing.id, event.partyName(), event.representativeName(), event.address(), editCount);
        } else {
            throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * registerアクションはParty未作成もしくはEditingからのみ実行可能
     */
    // registerアクション
    public Party register(RegisterEvent event) {
        return switch(this) {
            case PartyCreated partyCreated ->
                new PartyRegistered(partyCreated.id, event.partyName(), event.representativeName(), event.address(), event.remarks());
            case PartyEditing partyEditing ->
                new PartyRegistered(partyEditing.id, event.partyName(), event.representativeName(), event.address(), event.remarks());
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }

    public UUID getId() {
        return this.id;
    }
}
