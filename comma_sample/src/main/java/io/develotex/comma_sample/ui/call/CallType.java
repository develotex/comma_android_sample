package io.develotex.comma_sample.ui.call;

import java.util.HashMap;
import java.util.Map;

public enum CallType {

    INCOMING_AUDIO_CALL(1),
    INCOMING_VIDEO_CALL(2),
    OUTGOING_AUDIO_CALL(3),
    OUTGOING_VIDEO_CALL(4),
    AUDIO_CALL_IN_PROGRESS(5),
    VIDEO_CALL_IN_PROGRESS(6),
    CALL_ENDED(7);


    private int value;
    private static Map map = new HashMap<>();

    private CallType(int value) {
        this.value = value;
    }

    static {
        for (CallType callType : CallType.values()) {
            map.put(callType.value, callType);
        }
    }

    public static CallType valueOf(int callType) {
        return (CallType) map.get(callType);
    }

    public int getValue() {
        return value;
    }

}