package app.controller;

import app.config.WebServer;
import app.util.FileHelper;
import app.util.JpaUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class AbstractTest {

    private final static String populateTestDataSql = FileHelper.extractResourceContent("db/test-data.sql");

    @BeforeAll
    static void init() {

        WebServer webServer = new WebServer();
        webServer.start();

    }

    @BeforeEach
    void setUp() {
        JpaUtil.executeSql(populateTestDataSql);
    }

}
