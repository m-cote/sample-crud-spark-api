package app;

import app.config.WebConfig;
import app.util.HibernateUtil;

public class Application {

    public static void main(String[] args) {

        WebConfig webConfig = new WebConfig();
        ClassLoader classLoader = webConfig.getClass().getClassLoader();
        HibernateUtil.executeSql(classLoader.getResource("db/populateDb.sql"));
        webConfig.init();

    }

}
