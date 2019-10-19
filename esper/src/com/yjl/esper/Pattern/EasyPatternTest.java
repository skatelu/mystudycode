package com.yjl.esper.Pattern;


import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.yjl.esper.pojo.Apple;

class EasyPatternListener implements UpdateListener {

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents != null) {
            System.out.println("\nResult: ");
            for (int i = 0; i < newEvents.length; i++) {
                EventBean event = newEvents[i];
                System.out.println(event.get("price"));
            }
        }

        if(oldEvents != null){
            for (int i = 0; i < oldEvents.length; i++) {
                EventBean event = oldEvents[i];
                System.out.println("old" + event.get("price"));
            }
        }

    }
}

public class EasyPatternTest {

    public static void main(String[] args) throws InterruptedException {

        Configuration configuration = new Configuration();
        configuration.addEventType("Apple",Apple.class.getName());

        EPServiceProvider epService = EPServiceProviderManager.getProvider("myCEPEngine",configuration);
        EPAdministrator admin = epService.getEPAdministrator();
        EPRuntime runtime = epService.getEPRuntime();

        String apple = Apple.class.getName();

        String epl1 = "create context test1 partition by id from Apple";

        admin.createEPL(epl1);

        String epl2 = "context test1 select a5.price as price from " +
                "pattern" +
                "[every(a1 = Apple -> a2 = Apple -> a3 = Apple -> a4 = Apple -> a5 = Apple) where timer:within(1 seconds)] " +
                " ";

        EPStatement state = admin.createEPL(epl2);

        //绑定监听事件
        state.addListener(new EasyPatternListener());

        Apple apple1 = new Apple();
        apple1.setId(1);
        apple1.setPrice(1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        Thread.sleep(5000);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);
        runtime.sendEvent(apple1);

        runtime.sendEvent(apple1);

        Thread.sleep(5000);

        Apple apple2 = new Apple();
        apple2.setId(1);
        apple2.setPrice(2);
        runtime.sendEvent(apple2);

        //Thread.sleep(5000);

        Apple apple3 = new Apple();
        apple3.setId(2);
        apple3.setPrice(3);
        runtime.sendEvent(apple3);

        Apple apple4 = new Apple();
        apple4.setId(1);
        apple4.setPrice(4);
        runtime.sendEvent(apple4);

        Apple apple5 = new Apple();
        apple5.setId(1);
        apple5.setPrice(5);
        runtime.sendEvent(apple5);

        Thread.sleep(5000);

        Apple apple6 = new Apple();
        apple6.setId(1);
        apple6.setPrice(6);
        runtime.sendEvent(apple6);

        Apple apple7 = new Apple();
        apple7.setId(1);
        apple7.setPrice(7);
        runtime.sendEvent(apple7);

        Apple apple8 = new Apple();
        apple8.setId(1);
        apple8.setPrice(8);
        runtime.sendEvent(apple8);

        Apple apple9 = new Apple();
        apple9.setId(1);
        apple9.setPrice(9);
        runtime.sendEvent(apple9);

        Apple apple10 = new Apple();
        apple10.setId(1);
        apple10.setPrice(10);
        runtime.sendEvent(apple10);

    }
}
