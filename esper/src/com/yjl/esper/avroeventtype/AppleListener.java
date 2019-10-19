package com.yjl.esper.avroeventtype;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AppleListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null)
        {
            String avg = (String) newEvents[0].get("carId");
            System.out.println("The result is : " + avg);
        }

    }
}
