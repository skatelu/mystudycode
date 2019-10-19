package com.yjl.test.avro;

import com.espertech.esper.client.ConfigurationEventTypeAvro;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

import static com.espertech.esper.avro.core.AvroConstant.PROP_JAVA_STRING_KEY;
import static com.espertech.esper.avro.core.AvroConstant.PROP_JAVA_STRING_VALUE;
import static org.apache.avro.SchemaBuilder.record;

public class Test {

    public static void main(String[] args) {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        // Define CarLocUpdateEvent event type (example for runtime-configuration interface)构建需要的schema avro事件
        Schema schema = record("CarLocUpdateEvent").fields()
                .name("carId").type().stringBuilder().prop(PROP_JAVA_STRING_KEY, PROP_JAVA_STRING_VALUE).endString().noDefault()
                .requiredInt("direction").endRecord();

        //重新制定ConfigurationEventTypeAvro
        ConfigurationEventTypeAvro avroEvent = new ConfigurationEventTypeAvro(schema);
        //声明传入的事件类型，与需要的事件名称
        epService.getEPAdministrator().getConfiguration().addEventTypeAvro("CarLocUpdateEvent", avroEvent);

        EPAdministrator admin = epService.getEPAdministrator();

        String epl = "select * from CarLocUpdateEvent";

        EPStatement state = admin.createEPL(epl);
        state.addListener(new AppleListener());

        EPRuntime epRuntime = epService.getEPRuntime();

        //生成数据
        GenericData.Record event = new GenericData.Record(schema);
        event.put("carId", "A123456");
        event.put("direction", 1);

        //epService.getEPRuntime().sendEventAvro(event, "CarLocUpdateEvent");
        //发送事件给引擎
        epRuntime.sendEventAvro(event, "CarLocUpdateEvent");


    }
}
