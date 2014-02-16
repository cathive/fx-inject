package com.cathive.fx.cdi;

import javafx.application.Application;

import javax.enterprise.context.ApplicationScoped;

/**
 * The purpose of this class is to mark JavaFX applications that utilize CDI.
 *
 * <p>Application developers should <em>not</em> extend this class directly, but should rather
 * use one of the provided sub-classes, e.g. {@link com.cathive.fx.cdi.WeldApplication}.</p>
 *
 * @author Benjamin P. Jung
 */
abstract class CdiApplication extends Application {
}
