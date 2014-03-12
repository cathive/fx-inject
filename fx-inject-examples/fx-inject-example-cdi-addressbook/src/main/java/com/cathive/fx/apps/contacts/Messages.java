package com.cathive.fx.apps.contacts;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ResourceBundle;

/**
 * @author Benjamin P. Jung
 */
@Named("messages")
@Singleton
public class Messages {

    public static final String APP_NAME = "app.name";
    public static final String APP_TITLE = "app.title";
    public static final String APP_DESCRIPTION = "app.description";

    @Produces
    ResourceBundle getMessages() {
        return ResourceBundle.getBundle(Messages.class.getName());
    }

}
