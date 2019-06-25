package app;

import app.config.WebServer;
import app.util.FileHelper;
import app.util.JpaUtil;

public class Application {

    public static void main(String[] args) {

        new WebServer().start();
        JpaUtil.executeSql(FileHelper.extractResourceContent("db/populateDb.sql"));

    }

}
