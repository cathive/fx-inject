package com.cathive.fx.cdi.internal;

import com.cathive.fx.cdi.CdiFXMLLoader;
import com.cathive.fx.cdi.FXMLComponent;
import javafx.fxml.FXMLLoader;

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

        final FXMLComponent annotation = targetClass.getAnnotation(FXMLComponent.class);
        if (annotation == null) {
            throw new IllegalStateException(String.format("No @FXMLComponent annotation could be retrieved from class %s.", targetClass.getName()));
        }

        final FXMLLoader fxmlLoader = CdiFXMLLoader.create(
                annotation.location(),
                annotation.resources(),
                annotation.charset(),
                targetClass);

        fxmlLoader.setRoot(target);
        fxmlLoader.load();

    }

}
