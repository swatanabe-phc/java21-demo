package com.wemex.java21.demo.model.workflow.event;

public sealed interface WorkflowEvent
        permits RegisterEvent,
        ManualCheckStartEvent,
        ManualCheckFinishEvent,
        ManualApprovedEvent,
        TempSaveEvent,
        DeclineEvent {
}

