package com.wemex.java21.demo.repository;

import com.wemex.java21.demo.entity.EditHistory;
import com.wemex.java21.demo.mapper.EditHistoryMapper;
import com.wemex.java21.demo.mapper.RegisterHistoryMapper;
import com.wemex.java21.demo.model.workflow.event.RegisterEvent;
import com.wemex.java21.demo.model.workflow.event.TempSaveEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class EventRepository {

    private final EditHistoryMapper editHistoryMapper;
    private final RegisterHistoryMapper registerHistoryMapper;

    public EventRepository(EditHistoryMapper editHistoryMapper, RegisterHistoryMapper registerHistoryMapper) {
        this.editHistoryMapper = editHistoryMapper;
        this.registerHistoryMapper = registerHistoryMapper;
    }

    public void tempSave(TempSaveEvent tempSaveEvent) {
        editHistoryMapper.insert(tempSaveEvent);
    }

    public void register(RegisterEvent registerEvent) {
        registerHistoryMapper.insert(registerEvent);
    }

    public List<EditHistory> getHistory(UUID partyId) {
        return editHistoryMapper.selectByPartyId(partyId);
    }
}
