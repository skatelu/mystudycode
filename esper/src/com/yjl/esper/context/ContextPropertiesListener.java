package com.yjl.esper.context;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ContextPropertiesListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null)
        {
            EventBean event = newEvents[0];
            int a = newEvents.length;
            System.out.println("content.length "+ a +" context.name " + event.get("name") + ", context.id " + event.get("id") + ", context.key1 " + event.get("key1")
                    + ", context.key2 " + event.get("key2"));
        }

    }
}
