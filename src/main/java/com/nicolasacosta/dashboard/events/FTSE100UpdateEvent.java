package com.nicolasacosta.dashboard.events;

import com.nicolasacosta.dashboard.data.FTSE100Data;
import org.springframework.context.ApplicationEvent;

public class FTSE100UpdateEvent extends ApplicationEvent {

    public FTSE100UpdateEvent(FTSE100Data data) {
        super(data);
    }

    public FTSE100Data getData() {
        return (FTSE100Data) getSource();
    }
}
