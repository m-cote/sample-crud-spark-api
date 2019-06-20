package app;

import app.config.WebServer;
import app.util.FileHelper;
import app.util.HibernateUtil;

public class Application {

    public static void main(String[] args) {

        new WebServer().start();
        HibernateUtil.executeSql(FileHelper.extractResourceContent("db/populateDb.sql"));

    }

}
