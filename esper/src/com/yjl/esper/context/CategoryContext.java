package com.yjl.esper.context;


import com.espertech.esper.client.*;
import com.yjl.esper.pojo.Apple;

class ContextPropertiesListener4 implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents != null)
        {
            EventBean event = newEvents[0];
            System.out.println("context.name " + event.get("name") + ", context.id " + event.get("id") + ", context.label " + event.get("label"));
        }
    }
}

public class CategoryContext
{
    public static void main(String[] args)
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        EPAdministrator admin = epService.getEPAdministrator();
        EPRuntime runtime = epService.getEPRuntime();

        String esb = Apple.class.getName();
        String epl1 = "create context esbtest group by id<0 as low, group by id>0 and id<10 as middle,group by id>10 as high from " + esb;
        String epl2 = "context esbtest select context.id,context.name,context.label, price from " + esb;

        admin.createEPL(epl1);
        EPStatement state = admin.createEPL(epl2);
        state.addListener(new ContextPropertiesListener4());

        Apple e1 = new Apple();
        e1.setId(1);
        e1.setPrice(20);
        System.out.println("sendEvent: id=1, price=20");
        runtime.sendEvent(e1);


        Apple e2 = new Apple();
        e2.setId(0);
        e2.setPrice(30);
        System.out.println("sendEvent: id=0, price=30");
        runtime.sendEvent(e2);

        Apple e3 = new Apple();
        e3.setId(11);
        e3.setPrice(20);
        System.out.println("sendEvent: id=11, price=20");
        runtime.sendEvent(e3);

        Apple e4 = new Apple();
        e4.setId(-1);
        e4.setPrice(40);
        System.out.println("sendEvent: id=-1, price=40");
        runtime.sendEvent(e4);
    }
}
