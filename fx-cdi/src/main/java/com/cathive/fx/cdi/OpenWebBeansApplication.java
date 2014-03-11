package com.cathive.fx.cdi;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
public abstract class OpenWebBeansApplication extends CdiApplication {

    /** Logger for this instance. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /** OpenWebBeans lifecycle instance. */
    private ContainerLifecycle lifecycle = null;

    @Override
    public void init() throws Exception {

        super.init();

        logger.log(Level.INFO, "Initializing Apache OpenWebBeans...");

        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

    }

    @Override
    public void stop() throws Exception {
        if (this.lifecycle != null) {
            lifecycle.stopApplication(null);
        }
        super.stop();
    }

}
