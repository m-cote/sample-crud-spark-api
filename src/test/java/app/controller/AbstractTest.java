package app.controller;

import app.config.WebConfig;
import app.util.FileHelper;
import app.util.HibernateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractTest {

    private final static String populateTestDataSql = FileHelper.extractResourceContent("db/populateDb.sql");

    @BeforeAll
    static void init() {

        WebConfig webConfig = new WebConfig();
        webConfig.init();

    }

    @BeforeEach
    void setUp() {
        HibernateUtil.executeSql(populateTestDataSql);
    }

}
