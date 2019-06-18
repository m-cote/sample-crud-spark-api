package app.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;
import spark.utils.IOUtils;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HibernateUtil {

    private static volatile SessionFactory sessionFactory;
    private static final String HIBERNATE_CONFIG = System.getenv().getOrDefault("HIBERNATE_CONFIG", "db/hibernate.cfg.xml");

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    try {
                        sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                                .configure(HIBERNATE_CONFIG)
                                .build())
                                .getMetadataBuilder()
                                .build()
                                .getSessionFactoryBuilder()
                                .build();
                        return sessionFactory;

                    } catch (Throwable ex) {
                        System.err.println("Initial SessionFactory creation failed." + ex);
                        throw new ExceptionInInitializerError(ex);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void executeSql(URL sqlScriptUrl) {

        try (Session session = getSessionFactory().openSession()) {

            String sqlScript;
            try {
                sqlScript = IOUtils.toString(sqlScriptUrl.openStream());
            } catch (IOException e) {
                final String message = "Reading script \"" + sqlScriptUrl + "\" failed: " + e;
                System.err.println(message);
                throw new RuntimeException(message);
            }

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    Statement statement = connection.createStatement();
                    for (String sqlStatement : sqlScript.split(";")) {
                        if (StringUtils.isNotBlank(sqlStatement)) {
                            statement.addBatch(sqlStatement);
                        }
                    }
                    statement.executeBatch();
                }
            });
        }

    }

}