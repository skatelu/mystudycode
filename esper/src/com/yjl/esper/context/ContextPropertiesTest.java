package com.yjl.esper.context;

import com.espertech.esper.client.*;
import com.yjl.esper.pojo.Apple;

/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-7-31
 * Time: 13:58
 * <p>
 * Context基本语法
 * <p>
 * create context context_name partition [by] event_property [and event_property [and ...]] from stream_def
 * [, event_property [...] from stream_def] [, ...]
 * <p>
 * 说明：
 * context_name为context的名字，并且唯一。如果重复，会说明已存在。
 * event_property为事件的属性名，多个属性名之间用and连接，也可以用逗号连接。
 * stream_def为事件流的定义，简单的定义可以是一个事件的名称，比如之前定义了一个Map结构的事件为User，那么这里就可以写User。复杂的流定义后面会说到
 */
public class ContextPropertiesTest {


    /**
     * 简单的实例
     *
     * @return epl
     */
    public static void main(String[] args) {
        //创建
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

        EPAdministrator epAdministrator = epService.getEPAdministrator();

        String esb = Apple.class.getName();

        // 创建context
        String epl1 = "create context esbtest partition by id,price from " + esb;
        // context.id针对不同的esb的id,price建立一个context，如果事件的id和price相同，则context.id也相同，即表明事件进入了同一个context
        String epl2 = "context esbtest select context.id,context.name,context.key1,context.key2 from " + esb;

        epAdministrator.createEPL(epl1);
        EPStatement eplstate = epAdministrator.createEPL(epl2);

        eplstate.addListener(new ContextPropertiesListener());

        EPRuntime runtime = epService.getEPRuntime();

        Apple e1 = new Apple();
        e1.setId(1);
        e1.setPrice(20);
        System.out.println("sendEvent: id=1, price=20");
        runtime.sendEvent(e1);


        Apple e2 = new Apple();
        e2.setId(2);
        e2.setPrice(30);
        System.out.println("sendEvent: id=2, price=30");
        runtime.sendEvent(e2);

        Apple e3 = new Apple();
        e3.setId(1);
        e3.setPrice(20);
        System.out.println("sendEvent: id=1, price=20");
        runtime.sendEvent(e3);

        Apple e4 = new Apple();
        e4.setId(4);
        e4.setPrice(20);
        System.out.println("sendEvent: id=4, price=20");
        runtime.sendEvent(e4);

        Apple e5 = new Apple();
        e3.setId(1);
        e3.setPrice(30);
        System.out.println("sendEvent: id=1, price=30");
        runtime.sendEvent(e3);

    }


}
