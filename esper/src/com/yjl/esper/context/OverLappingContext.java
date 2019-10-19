package com.yjl.esper.context;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.yjl.esper.pojo.Apple;
import com.yjl.esper.pojo.EndEvent;
import com.yjl.esper.pojo.StartEvent;

class OverLappingContextListener implements UpdateListener
    {

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents != null)
            {
                EventBean event = newEvents[0];
                System.out.println("context.id:" + event.get("id") + ", id:" + event.get("id"));
            }
        }
    }

    class OverLappingContextListener2 implements UpdateListener
    {

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents != null)
            {
                EventBean event = newEvents[0];
                System.out.println("Class:" + event.getUnderlying().getClass().getName() + ", id:" + event.get("id"));
            }
        }
    }

    public class OverLappingContext
    {
        public static void main(String[] args)
        {
            EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
            EPAdministrator admin = epService.getEPAdministrator();
            EPRuntime runtime = epService.getEPRuntime();

            String initial = StartEvent.class.getName();
            String terminate = EndEvent.class.getName();
            String some = Apple.class.getName();
            // 以StartEvent事件作为初始事件，EndEvent事件作为终结事件
            String epl1 = "create context OverLapping initiated " + initial + " terminated " + terminate;
            String epl2 = "context OverLapping select context.id from " + initial;
            String epl3 = "context OverLapping select * from " + some;

            admin.createEPL(epl1);
            EPStatement state = admin.createEPL(epl2);
            state.addListener(new OverLappingContextListener());
            EPStatement state1 = admin.createEPL(epl3);
            state1.addListener(new OverLappingContextListener2());


            StartEvent i = new StartEvent();
            System.out.println("sendEvent: StartEvent");
            runtime.sendEvent(i);

            Apple s = new Apple();
            s.setId(2);
            System.out.println("sendEvent: SomeEvent");
            runtime.sendEvent(s);

            StartEvent i2 = new StartEvent();
            System.out.println("sendEvent: StartEvent");
            runtime.sendEvent(i2);

            StartEvent i3 = new StartEvent();
            System.out.println("sendEvent: StartEvent");
            runtime.sendEvent(i2);

            EndEvent t = new EndEvent();
            System.out.println("sendEvent: EndEvent");
            runtime.sendEvent(t);

            Apple s2 = new Apple();
            s2.setId(4);
            System.out.println("sendEvent: Apple");
            runtime.sendEvent(s2);
        }
    }

