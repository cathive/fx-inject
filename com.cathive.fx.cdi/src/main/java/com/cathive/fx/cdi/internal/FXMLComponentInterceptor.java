package com.cathive.fx.cdi.internal;

import com.cathive.fx.cdi.CdiFXMLLoader;
import com.cathive.fx.cdi.FXMLComponent;
import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.spi.CDI;
import javax.interceptor.AroundConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * This factory is responsible for the production of CDI-aware FXML-based custom components that
 * have been annotated as such using the {@link com.cathive.fx.cdi.FXMLComponent @FXMLComponent} annotation.
 * @author Benjamin P. Jung
 * @since 1.1.0
 */
@FXMLComponent
@Interceptor
class FXMLComponentInterceptor {

    @AroundConstruct
    public void createCdiFXMLComponent(final InvocationContext invocationContext) throws Exception {

        // Performs c-tor invocation. Afterwards "invocationContext.getTarget()" will no longer return "null".
        invocationContext.proceed();

        // Fetches the newly created annotated object and it's class.
        final Object target = invocationContext.getTarget();
        final Class<?> targetClass = target.getClass();

        final CdiFXMLLoader fxmlLoader = CDI.current().select(CdiFXMLLoader.class).get();

        final FXMLComponent annotation = targetClass.getAnnotation(FXMLComponent.class);
        FXMLLoaderFactory.initializeFXMLLoader(fxmlLoader, targetClass, annotation.location(), annotation.resources(), annotation.charset());

        fxmlLoader.setRoot(target);
        fxmlLoader.setController(target);

        fxmlLoader.load();

    }

}
