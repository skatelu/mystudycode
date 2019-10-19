package com.yjl.esper.io_mysql;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;

import java.util.Iterator;

public class IteratorSQLTest {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = Config_DB.getConnectPGSql();
        config.addVariable("vari", Long.class, 59230022263L);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

        EPAdministrator admin = epService.getEPAdministrator();
        EPRuntime runtime = epService.getEPRuntime();
        // id=1, name="luonq"
        String epl1 = "select fundid, fundname from sql:ktrc_dev['select fundid, fundname from fundinfo where fundid=${vari}']";

        EPStatement state = admin.createEPL(epl1);

        Iterator<EventBean> iter = state.iterator(); // 也可以调用safeIterator方法，该方法以线程安全方式查询DB
        while (iter.hasNext()) {
            EventBean eventBean = iter.next();
            System.out.println(eventBean.get("fundid") + " " + eventBean.get("fundname"));
        }
    }

}
