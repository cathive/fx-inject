package com.cathive.fx.cdi;

import com.cathive.fx.inject.core.FXMLLoaderParams;
import javafx.fxml.FXMLLoader;

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


    /**
     * Default (private) constructor.
     * <p>Use the {@link #create(String, String, String, Class)} method instead
     * to retrieve new instances of this class.</p>
     */
    private CdiFXMLLoader() {

        super();

        // Uses the currently loaded CDI implementation to look up controller classes
        // that have been specified via "fx:controller='...'" in our FXML files.
        this.setControllerFactory((aClass) -> CDI.current().select(aClass));

        // Uses CDI to instantiate all components within our FXML files.
        this.setBuilderFactory(aClass -> () -> CDI.current().select(aClass).get());

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
     *         {@link com.cathive.fx.inject.core.FXMLLoaderParams @FXMLLoaderParams} or a class that has been marked as
     *         with @FXMLComponent}.
     * @return
     *          A new CDI-aware FXMLLoader instance.
     */
    public static CdiFXMLLoader create(final String location, final String resources, final String charset, final Class<?> declaringClass) {

        assert declaringClass != null;

        final CdiFXMLLoader fxmlLoader = new CdiFXMLLoader();

        // Checks the location that has been specified (if any) and uses the default
        // class loader to create an URL that points to a FXML file on the classpath.
        if (location != null && !location.equals(FXMLLoaderParams.LOCATION_UNSPECIFIED)) {
            final URL locationUrl = declaringClass.getResource(location);
            if (locationUrl == null) {
                throw new IllegalArgumentException(String.format("Couldn't find FXML file: \"%s\".", location));
            }
            fxmlLoader.setLocation(locationUrl);
        }

        if (charset != null && !charset.equals(FXMLLoaderParams.CHARSET_UNSPECIFIED)) {
            fxmlLoader.setCharset(Charset.forName(charset));
        }

        if (resources != null && !resources.equals(FXMLLoaderParams.RESOURCES_UNSPECIFIED)) {
            fxmlLoader.setResources(ResourceBundle.getBundle(resources));
        }

        return fxmlLoader;

    }

}
