package com.yjl.esper.start;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.yjl.esper.context.ContextPropertiesTest;
import com.yjl.esper.listener.AppleListener;
import com.yjl.esper.pojo.Apple;

public class Test {

    public static void main(String[] args) {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

        EPAdministrator admin = epService.getEPAdministrator();

        String product = Apple.class.getName();

        String epl = "select avg(price) from " + product + ".win:length_batch(3)";
        //String epl = new ContextPropertiesTest().simple();

        EPStatement state = admin.createEPL(epl);
        state.addListener(new AppleListener());

        EPRuntime epRuntime = epService.getEPRuntime();

        Apple apple1 = new Apple();
        apple1.setId(1);
        apple1.setPrice(5);
        epRuntime.sendEvent(apple1);

        Apple apple2 = new Apple();
        apple2.setId(2);
        apple2.setPrice(2);
        epRuntime.sendEvent(apple2);

        Apple apple3 = new Apple();
        apple3.setId(3);
        apple3.setPrice(5);
        epRuntime.sendEvent(apple3);



    }
}
