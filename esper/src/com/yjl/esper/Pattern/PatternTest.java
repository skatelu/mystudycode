package com.yjl.esper.Pattern;

import com.espertech.esper.client.*;
import com.yjl.esper.pojo.ConsumeEvent;

class PatternConsumeListener1 implements UpdateListener {

        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            if (newEvents != null) {
                for (int i = 0; i < newEvents.length; i++) {
                    System.out.println("a: " + newEvents[i].get("a"));
                    System.out.println("b: " + newEvents[i].get("b"));
                }
            }
        }
    }

    class PatternConsumeListener2 implements UpdateListener {

        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            if (newEvents != null) {
                for (int i = 0; i < newEvents.length; i++) {
                    System.out.println("a: " + newEvents[i].get("a"));
                    System.out.println("b: " + newEvents[i].get("b"));
                    System.out.println("c: " + newEvents[i].get("c"));
                }
            }
        }
    }

    public class PatternTest {

        public static void main(String[] args) {
            EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
            EPAdministrator admin = epService.getEPAdministrator();
            EPRuntime runtime = epService.getEPRuntime();

            String consume = ConsumeEvent.class.getName();

            //创建pattern事件
            //
            String epl1 = "every (a=" + consume + "(id = 1)@consume and b=" + consume + "(name = 'luonq'))";
            System.out.println("EPL1: " + epl1 + "\n");

            EPStatement stat1 = admin.createPattern(epl1);
            stat1.addListener(new PatternConsumeListener1());

            ConsumeEvent ce1 = new ConsumeEvent();
            ce1.setId(1);
            ce1.setName("luonq");
            System.out.println("Send Event: " + ce1);
            runtime.sendEvent(ce1);

            System.out.println();

            ConsumeEvent ce2 = new ConsumeEvent();
            ce2.setId(2);
            ce2.setName("luonq");
            System.out.println("Send Event: " + ce2);
            runtime.sendEvent(ce2);

            stat1.destroy();
            System.out.println();

            String epl2 = "every (a=" + consume + "(id = 1)@consume(2) or b=" + consume + "(name = 'luonq')@consume(3) or c=" + consume + "(age > 2))";
            System.out.println("EPL2: " + epl2 + "\n");

            EPStatement stat2 = admin.createPattern(epl2);
            stat2.addListener(new PatternConsumeListener2());

            ConsumeEvent ce3 = new ConsumeEvent();
            ce3.setId(1);
            ce3.setName("luonq");
            ce3.setAge(3);
            System.out.println("Send Event: " + ce3);
            runtime.sendEvent(ce3);

            ConsumeEvent ce4 = new ConsumeEvent();
            ce4.setId(1);
            ce4.setName("luonqin");
            ce4.setAge(1);
            System.out.println("Send Event: " + ce4);
            runtime.sendEvent(ce4);

            ConsumeEvent ce5 = new ConsumeEvent();
            ce5.setId(3);
            ce5.setName("luonqin");
            ce5.setAge(5);
            System.out.println("Send Event: " + ce5);
            runtime.sendEvent(ce5);
        }
    }

