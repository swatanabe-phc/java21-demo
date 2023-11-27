package com.wemex.java21.demo.repository;

import com.wemex.java21.demo.entity.PartyEntity;
import com.wemex.java21.demo.mapper.EditHistoryMapper;
import com.wemex.java21.demo.mapper.PartyMapper;
import com.wemex.java21.demo.mapper.RegisterHistoryMapper;
import com.wemex.java21.demo.model.party.Party;
import com.wemex.java21.demo.model.party.PartyCreated;
import com.wemex.java21.demo.model.party.PartyEditing;
import com.wemex.java21.demo.model.party.PartyRegistered;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class PartyRepository {

    private final PartyMapper partyMapper;
    private final EditHistoryMapper editHistoryMapper;
    private final RegisterHistoryMapper registerHistoryMapper;

    public PartyRepository(PartyMapper partyMapper, EditHistoryMapper editHistoryMapper, RegisterHistoryMapper registerHistoryMapper) {
        this.partyMapper = partyMapper;
        this.editHistoryMapper = editHistoryMapper;
        this.registerHistoryMapper = registerHistoryMapper;
    }

    public void save(Party party) {
        switch(party) {
            case PartyEditing partyEditing -> {
                partyMapper.update(partyEditing);
                // PartyRepositoryの中でインサートするとeventを取得してUserIdや日時を保存する必要があるのでRepositoryを分ける方が良さそう
                // editHistory.insert(partyEditing.getId(), partyEditing.getName(), partyEditing.getRepresentativeName(), partyEditing.getAddress(), "userId", LocalDateTime.now());
            }
            case PartyRegistered partyRegistered -> partyMapper.update(partyRegistered);
            default -> throw new IllegalStateException("編集中の取引先のみ一時保存できます。現在のクラス: " + party.getClass());
        }
        System.out.println("saved " + party.getId());
    }

    /**
     * 基本となる取引先情報（取引先名、代表者名、住所）を取得しつつ、状態に応じて編集回数や備考を取得する
     * @param partyId
     * @return
     */
    @NotNull
    public Party getParty(UUID partyId) {
        // 対象のpartyIdがなければ新規作成のPartyを返すことでNotNullにできる
        if(partyId == null) return new PartyCreated();
        // stateを表すentityをDBから取得
        PartyEntity partyEntity = partyMapper.selectById(partyId);

        return switch(partyEntity.state()) {
            case "EDITING" -> {
                int editCount = editHistoryMapper.selectByPartyId(partyId).size();
                yield new PartyEditing(
                        partyEntity.id(),
                        partyEntity.name(),
                        partyEntity.representativeName(),
                        partyEntity.address(),
                        editCount
                );
            }
            case "REGISTERED" -> {
                String remarks = registerHistoryMapper.selectByPartyId(partyId).remarks();
                yield new PartyRegistered(
                        partyEntity.id(),
                        partyEntity.name(),
                        partyEntity.representativeName(),
                        partyEntity.address(),
                        remarks
                );
            }
            default -> throw new IllegalStateException("Unexpected value: " + partyEntity.state());
        };
    }


}
