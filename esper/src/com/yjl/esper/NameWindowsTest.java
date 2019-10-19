package com.yjl.esper;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.yjl.esper.pojo.SelectEvent;

class SelectNamedWindowListener implements UpdateListener{

        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            if (newEvents != null) {
                System.out.println("There is "+newEvents.length+" events to be return!");
                for (int i = 0;  i < newEvents.length;i++) {
                    System.out.println(newEvents[i].getUnderlying());
                }
            }
        }
    }
    public class NameWindowsTest{

        public static void main(String[] args) {
            EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
            EPAdministrator admin = epService.getEPAdministrator();
            EPRuntime runtime = epService.getEPRuntime();

            String selectEvent = SelectEvent.class.getName();

            //创建一个Window 长度为3的时候出发监听，输出事件
            String epl1 = "create window SelectNamedWindow.win:length_batch(3) as " + selectEvent;
            admin.createEPL(epl1);

            System.out.println("Create named window: create window SelectNamedWindow.win:length_batch(3) as "+selectEvent);
            //向window中插入事件
            String epl2 = "insert into SelectNamedWindow select * from " + selectEvent;
            admin.createEPL(epl2);


            SelectEvent se1 = new SelectEvent();
            se1.setName("se1");
            se1.setPrice(1);
            System.out.println("发送 SelecEvent1 " + se1);
            runtime.sendEvent(se1);

            SelectEvent se2 = new SelectEvent();
            se2.setName("se2");
            se2.setPrice(2);
            System.out.println("发送 SelecEvent2 " + se2);
            runtime.sendEvent(se2);

            // Can't create "select * from SelectamedWindow.win:time(3 sec)"
            String epl3 = "select * from SelectNamedWindow(price>=2)";
            EPStatement state3 = admin.createEPL(epl3);
            state3.addListener(new SelectNamedWindowListener());
            System.out.println("Register select sentence: select * from SelectNamedWindow(price>=2)");

            SelectEvent se3 = new SelectEvent();
            se3.setName("se3");
            se3.setPrice(3);
            System.out.println("发送 SelecEvent3 " + se3 + "\n");
            runtime.sendEvent(se3);

            SelectEvent se4 = new SelectEvent();
            se4.setName("se1");
            se4.setPrice(1);
            System.out.println("发送 SelecEvent4 " + se4);
            runtime.sendEvent(se4);

            SelectEvent se5 = new SelectEvent();
            se5.setName("se2");
            se5.setPrice(2);
            System.out.println("发送 SelecEvent5 " + se5);
            runtime.sendEvent(se5);

            SelectEvent se6 = new SelectEvent();
            se6.setName("se3");
            se6.setPrice(3);
            System.out.println("发送 SelecEvent6 " + se6 + "\n");
            runtime.sendEvent(se6);

        }
    }
