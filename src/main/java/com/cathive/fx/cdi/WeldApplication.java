package com.cathive.fx.cdi;

import org.jboss.weld.environment.se.Weld;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Benjamin P. Jung
 */
public abstract class WeldApplication extends CdiApplication {

    /** Logger for this instance. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * JBoss WELD instance to be used for all CDI-related operations.
     * <p>This field will be populated during invocation of the {@link #init()}-method.</p>
     */
    private Weld weld;

    @Override
    public void init() throws Exception {

        logger.log(Level.INFO, "Initializing Weld-based JavaFX appplication: {0}", this.getClass().getName());

        // Sets the JavaFX application instance to be used when injecting an instance of this class.
        FxCdiExtension.setJavaFxApplication(this);

        logger.log(Level.INFO, "Initializing JBoss WELD...");
        this.weld = new Weld();
        this.weld.initialize();

    }

}
