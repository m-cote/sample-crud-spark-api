package app;

import app.config.WebConfig;
import app.util.FileHelper;
import app.util.HibernateUtil;

public class Application {

    public static void main(String[] args) {

        WebConfig webConfig = new WebConfig();
        HibernateUtil.executeSql(FileHelper.extractResourceContent("db/populateDb.sql"));
        webConfig.init();

    }

}
