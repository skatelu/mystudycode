package com.yjl.esper.io_mysql;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationDBRef;

public class Config_DB {

    public static Configuration getConnectPGSql(){
        ConfigurationDBRef dbConfig = new ConfigurationDBRef();
        dbConfig.setDriverManagerConnection("org.postgresql.Driver",
                "jdbc:postgresql://10.29.182.50:5432/ktrc_dev",
                "postgres",
                "postgres");

        Configuration engineConfig = new Configuration();
        engineConfig.addDatabaseReference("ktrc_dev", dbConfig);

        return engineConfig;
        //esperEngine = EPServiceProviderManager.getDefaultProvider(engineConfig);
    }

    public static Configuration getConnectMYSql(){
        ConfigurationDBRef dbConfig = new ConfigurationDBRef();
        dbConfig.setDriverManagerConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/test",
                "root",
                "123456");

        Configuration engineConfig = new Configuration();
        engineConfig.addDatabaseReference("test", dbConfig);

        return engineConfig;
        //esperEngine = EPServiceProviderManager.getDefaultProvider(engineConfig);
    }

}
