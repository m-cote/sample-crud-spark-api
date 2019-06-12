package app.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
}