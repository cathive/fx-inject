package com.cathive.fx.apps.contacts;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjamin P. Jung
 */
@Named
@Singleton
public class DataStorage {

    /** Name of the persistence unit to be used when working with contacts. */
    public static final String PERSISTENCE_UNIT_NAME = "contacts";

    private final Map<String, String> persistenceProperties;

    public DataStorage() {
        super();
        persistenceProperties = new HashMap<>();
        persistenceProperties.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        persistenceProperties.put("javax.persistence.jdbc.url", "jdbc:derby:" + lookupLocalStorageDirectory().getAbsolutePath() + "/db;create=true");
    }

    @Produces
    @Named("localStorageDirectory")
    private File lookupLocalStorageDirectory() {
        final File homeDirectory = new File(System.getProperty("user.home"));
        return new File(homeDirectory, ".cathive/Contacts");
    }

    @Produces
    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceProperties).createEntityManager();
    }

}
