package com.yjl.test.avro;

import com.espertech.esper.client.ConfigurationEventTypeAvro;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import org.apache.avro.Schema;

import static com.espertech.esper.avro.core.AvroConstant.PROP_JAVA_STRING_KEY;
import static com.espertech.esper.avro.core.AvroConstant.PROP_JAVA_STRING_VALUE;
import static org.apache.avro.SchemaBuilder.record;

public class TestEsperAvro {

    private static EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

    public static void main(String[] args) {
        // Define CarLocUpdateEvent event type (example for runtime-configuration interface)
        Schema schema = record("CarLocUpdateEvent").fields()
                .name("carId").type().stringBuilder().prop(PROP_JAVA_STRING_KEY, PROP_JAVA_STRING_VALUE).endString().noDefault()
                .requiredInt("direction")
                .endRecord();
        ConfigurationEventTypeAvro avroEvent = new ConfigurationEventTypeAvro(schema);
        epService.getEPAdministrator().getConfiguration().addEventTypeAvro("CarLocUpdateEvent", avroEvent);
    }
}
