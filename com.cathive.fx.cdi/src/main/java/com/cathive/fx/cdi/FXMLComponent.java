package com.cathive.fx.cdi;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * This annotation is used to mark classes as FXML-based custom components.
 *
 * @author Benjamin P. Jung
 */
@InterceptorBinding
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, FIELD, PARAMETER, TYPE })
public @interface FXMLComponent {

    /**
     * Sets the location used to resolve relative path attribute values.
     * @return
     *         The location used to resolve relative path attribute values.
     * @see javafx.fxml.FXMLLoader#setLocation(java.net.URL)
     */
    @Nonbinding
    String location() default CdiFXMLLoader.LOCATION_UNSPECIFIED;

    /**
     * Sets the resources used to resolve resource key attribute values.
     * @return
     *         The resources used to resolve resource key attribute values.
     * @see javafx.fxml.FXMLLoader#setResources(java.util.ResourceBundle)
     */
    @Nonbinding
    String resources() default CdiFXMLLoader.RESOURCES_UNSPECIFIED;

    /**
     * Sets the charset used by the configured loader.
     * @return
     *          The charset used by the configured loader.
     * @see javafx.fxml.FXMLLoader#setCharset(java.nio.charset.Charset)
     */
    @Nonbinding
    String charset() default CdiFXMLLoader.CHARSET_UNSPECIFIED;

}