package com.cathive.fx.cdi;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

/**
 * CDI-aware FXMLLoader
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
public class CdiFXMLLoader extends FXMLLoader {

    // Some marker strings for unspecified values.
    public static final String LOCATION_UNSPECIFIED = "$$$LOCATION_UNSPECIFIED$$$";
    public static final String RESOURCES_UNSPECIFIED = "$$$RESOURCES_UNSPECIFIED$$$";
    public static final String CHARSET_UNSPECIFIED = "$$$CHARSET_UNSPECIFIED$$$";

    /**
     * A reference to all the package names that won't work as supposed to when trying
     * to instantiate beans from within via CDI in conjunction with FXML builders.
     * <p>This will avoid an exception message caused by a {@link com.sun.javafx.fxml.PropertyNotFoundException}
     * <em>'Property "${propertyName}" does not exist or is read-only'</em>.</p>
     * @see com.sun.javafx.fxml.PropertyNotFoundException
     */
    private static final String[] CDI_BLACKLIST = new String[] {
            "javafx",
            "java.awt",
            "javax.swing"
    };

    /** A default (non-CDI aware) JavaFX builder factory. */
    private final JavaFXBuilderFactory defaultBuilderFactory;

    /**
     * Default (private) constructor.
     * <p>Use the {@link #create(String, String, String, Class)} method to retrieve new instances of this class.</p>
     */
    private CdiFXMLLoader() {

        super();

        // Uses the currently loaded CDI implementation to look up controller classes
        // that have been specified via "fx:controller='...'" in our FXML files.
        this.setControllerFactory((aClass) -> CDI.current().select(aClass));

        // Initializes the default JavaFX builder factory to be used for non CDI-aware beans.
        this.defaultBuilderFactory = new JavaFXBuilderFactory();

        this.setBuilderFactory((aClass) -> {

            // Uses the default builder factory to retrieve builders where applicable.
            final Builder<?> defaultBuilder = defaultBuilderFactory.getBuilder(aClass);
            if (defaultBuilder != null) { return defaultBuilder; }

            // Make sure that we use a "null" builder for components that are 'blacklisted'.
            final String packageName = aClass.getPackage().getName();
            for (final String blacklisted: CDI_BLACKLIST) {
                if (packageName.startsWith(blacklisted)) {
                    return null;
                }
            }

            // Uses CDI to retrieve a bean of the desired class.
            final Instance<?> cdiBean = CDI.current().select(aClass);
            if (cdiBean != null && !cdiBean.isUnsatisfied()) {
                return new Builder<Object>() {
                    @Override
                    public Object build() {
                        return cdiBean.get();
                    }
                };
            }

            throw new IllegalStateException(String.format("Unable to construct bean of class %s", aClass.getName()));

        });

    }

    @Override
    public String toString() {
        return String.format("[CDI-aware] %s", super.toString());
    }

    /**
     * Creates a new CDI-aware FXMLLoader instance.
     * @param location
     *         Location of the FXML file to be loaded.
     * @param charset
     *         Charset to be used when loading the specified FXML file.
     * @param resources
     *         Location of the {@link java.util.ResourceBundle} to be used when localizing contents
     *         in the FXML resources.
     * @param declaringClass
     *         The declaring class.
     *         Usually this is either a field of type {@link javafx.fxml.FXMLLoader} that has been annotated with
     *         {@link com.cathive.fx.cdi.FXMLLoaderParams @FXMLLoaderParams} or a class that has been marked as
     *         with {@link com.cathive.fx.cdi.FXMLComponent @FXMLComponent}.
     * @return
     *          A new CDI-aware FXMLLoader instance.
     */
    public static CdiFXMLLoader create(final String location, final String resources, final String charset, final Class<?> declaringClass) {

        assert declaringClass != null;

        final CdiFXMLLoader fxmlLoader = new CdiFXMLLoader();

        // Checks the location that has been specified (if any) and uses the default
        // class loader to create an URL that points to a FXML file on the classpath.
        if (location != null && !location.equals(LOCATION_UNSPECIFIED)) {
            final URL locationUrl = declaringClass.getResource(location);
            if (locationUrl == null) {
                throw new IllegalArgumentException(String.format("Couldn't find FXML file: \"%s\".", location));
            }
            fxmlLoader.setLocation(locationUrl);
        }

        if (charset != null && !charset.equals(CHARSET_UNSPECIFIED)) {
            fxmlLoader.setCharset(Charset.forName(charset));
        }

        if (resources != null && !resources.equals(RESOURCES_UNSPECIFIED)) {
            fxmlLoader.setResources(ResourceBundle.getBundle(resources));
        }

        return fxmlLoader;

    }

}
