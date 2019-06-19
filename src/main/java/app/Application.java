package app;

import app.config.WebConfig;
import app.util.FileHelper;
import app.util.HibernateUtil;

public class Application {

    public static void main(String[] args) {

        new WebConfig().init();
        HibernateUtil.executeSql(FileHelper.extractResourceContent("db/populateDb.sql"));

    }

}
