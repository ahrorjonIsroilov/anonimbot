package anonim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    DEFAULT("default", true),
    SEND_MESSAGE("send_message", true),
    SEND_ADS("send_ads", true),
    ANSWER("answer", true),
    SET_ID("set_id", true);
    private final String code;
    private final boolean afterJoined;
}
