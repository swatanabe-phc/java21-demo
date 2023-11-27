package com.wemex.java21.demo.controller;

import com.wemex.java21.demo.entity.EditHistory;
import com.wemex.java21.demo.model.party.Party;
import com.wemex.java21.demo.model.workflow.event.RegisterEvent;
import com.wemex.java21.demo.model.workflow.event.TempSaveEvent;
import com.wemex.java21.demo.repository.EventRepository;
import com.wemex.java21.demo.repository.PartyRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PartyController {

    private final PartyRepository partyRepository;
    private final EventRepository eventRepository;

    public PartyController(PartyRepository partyRepository, EventRepository eventRepository) {
        this.partyRepository = partyRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/party/tempSave")
    @Transactional
    public Map<String, UUID>  tempSave(@RequestBody TempSaveRequest request) {
        Party party = partyRepository.getParty(request.partyId);
        TempSaveEvent tempSaveEvent = new TempSaveEvent(
                UUID.randomUUID(),
                party.getId(),
                request.partyName,
                request.representativeName,
                request.address,
                LocalDateTime.now(),
                request.userId);
        // コントローラーは遷移後のPartyの状態を知らなくてよい
        Party tempSavedParty = party.tempSave(tempSaveEvent);
        partyRepository.save(tempSavedParty);
        // コントローラーはどういうイベントが発生したか知っているので保存先を知っている
        eventRepository.tempSave(tempSaveEvent);

        return Map.of("partyId", party.getId());
    }

    @PostMapping("/party/register")
    @Transactional
    public Map<String, UUID> register(@RequestBody RegisterRequest request) {
        Party party = partyRepository.getParty(request.partyId);
        RegisterEvent registerEvent = new RegisterEvent(
                UUID.randomUUID(),
                // 下書きなしで登録する場合は新規作成時はpartyIdがNull
                party.getId(),
                request.partyName,
                request.representativeName,
                request.address,
                LocalDateTime.now(),
                request.userId,
                request.remarks // 備考は申請時に設定できる
        );
        Party registeredParty = party.register(registerEvent);
        partyRepository.save(registeredParty);
        eventRepository.register(registerEvent);

        return  Map.of("partyId", party.getId());

    }

    @GetMapping("/party/{partyId}/history")
    public HistoryResponse getHistory(@PathVariable UUID partyId) {
        List<EditHistory> histories = eventRepository.getHistory(partyId);
        return new HistoryResponse(
                Map.of("editHistory", histories)
        );
    }

    private record TempSaveRequest(@Nullable UUID partyId, String partyName, String representativeName, String address, String userId) {}
    private record RegisterRequest(@Nullable UUID partyId, String partyName, String representativeName, String address, String userId, String remarks) {}
    private record HistoryResponse(Map<String, List<EditHistory>> histories) {}

}

