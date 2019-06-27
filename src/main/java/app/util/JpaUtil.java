package app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaUtil {

    private static final Logger log = LoggerFactory.getLogger(ErrorResponder.class);

    private static final String PERSISTENCE_CONFIG;

    static {
        String persistenceConfig = System.getenv().get("PERSISTENCE_CONFIG");
        if (persistenceConfig == null) {
            URL resource = JpaUtil.class.getClassLoader().getResource("db/persistence.properties");
            Objects.requireNonNull(resource, "Default persistence config file not found");
            persistenceConfig = resource.getPath();
        }
        PERSISTENCE_CONFIG = persistenceConfig;
    }

    private static volatile EntityManagerFactory EM_FACTORY;
    private static final String PERSISTENCE_UNIT_NAME = "hibernate-persistence";

    private static EntityManagerFactory getFactory() {
        if (EM_FACTORY == null) {
            synchronized (JpaUtil.class) {
                if (EM_FACTORY == null) {
                    try {
                        Properties props = new Properties();
                        props.load(new FileInputStream(PERSISTENCE_CONFIG));
                        EM_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, props);
                    } catch (Throwable e) {
                        log.error("EntityManagerFactory initialization failed.", e);
                        throw new ExceptionInInitializerError(e);
                    }
                }
            }
        }
        return EM_FACTORY;
    }

    public static EntityManager createEntityManager() {
        return getFactory().createEntityManager();
    }

    public static Session openSession() {
        return getFactory().unwrap(SessionFactory.class).openSession();
    }

    public static void executeSql(String sql) {

        try (Session session = openSession()) {
            session.doWork(connection -> {
                Statement statement = connection.createStatement();
                for (String sqlStatement : sql.split(";")) {
                    if (StringUtils.isNotBlank(sqlStatement)) {
                        statement.addBatch(sqlStatement);
                    }
                }
                statement.executeBatch();
            });
        }
    }
}
