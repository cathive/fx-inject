/*
 * Copyright (C) 2013,2014 The Cat Hive Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cathive.fx.cdi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This JavaFX extension makes sure that sub-classes of {@link com.cathive.fx.cdi.CdiApplication} can be
 * injected (using the {@link javax.inject.Inject @javax.inject.Inject} annotation).
 * @author Benjamin P. Jung
 */
class CdiExtension implements Extension {

    /** Logger for this instance. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * The JavaFX application instance to be provided by the CDI BeanManager.
     * <p>It is crucial that this field is populated (using {@link #setJavaFxApplication(CdiApplication)}
     * prior to initializing the CDI context.</p>
     */
    static CdiApplication JAVA_FX_APPLICATION;

    static CdiApplicationBean<CdiApplication> JAVA_FX_APPLICATION_BEAN;

    /**
     * Sets the JavaFX application instance to be provided by the CDI BeanManager.
     * @param javaFxApplication
     *         The JavaFX application instance to be provided by the CDI BeanManager.
     */
    public static void setJavaFxApplication(final CdiApplication javaFxApplication) {

        assert JAVA_FX_APPLICATION == null;
        assert javaFxApplication != null;

        JAVA_FX_APPLICATION = javaFxApplication;

    }

    public <T extends Application> T getJavaFxApplication() {
        return (T) JAVA_FX_APPLICATION;
    }


    // ---- ==== CDI LIFECYCLE EVENTS ==== -----------------------------------------------------------------------------

    void beforeBeanDiscovery(final @Observes BeforeBeanDiscovery beforeBeanDiscovery, final BeanManager beanManager) {

        if (JAVA_FX_APPLICATION == null) {
            throw new IllegalStateException("JavaFX application was not provided. Did you call FxCdiExtension.setJavaFxApplication(..)?");
        }

    }

    <T> void processAnnotatedType(final @Observes ProcessAnnotatedType<? extends Application> pat, final BeanManager beanManager) {
        final Class<? extends Application> javaClass = pat.getAnnotatedType().getJavaClass();
        if (javaClass.equals(JAVA_FX_APPLICATION.getClass())) {
            // We veto any sub-classes of CdiApplication to make sure, that the right bean
            // will be pulled into the dependency graph when using @Inject annotations.
            pat.veto();
        }
    }

    <X> void processInjectionTarget(final @Observes ProcessInjectionTarget<X> pit, final BeanManager beanManager) {
        final Class<X> javaClass = pit.getAnnotatedType().getJavaClass();
        if (javaClass.equals(JAVA_FX_APPLICATION.getClass())) {

        }
    }

    <T, X> void processInjectionPoint(final @Observes ProcessInjectionPoint<T, X> pip, final BeanManager beanManager) {
    }

    void afterBeanDiscovery(final @Observes AfterBeanDiscovery abd, final BeanManager beanManager) {

        final AnnotatedType<CdiApplication> annotatedType = (AnnotatedType<CdiApplication>) beanManager.createAnnotatedType(JAVA_FX_APPLICATION.getClass());
        final BeanAttributes<CdiApplication> beanAttributes = beanManager.createBeanAttributes(annotatedType);
        final InjectionTarget<CdiApplication> injectionTarget = beanManager.createInjectionTarget(annotatedType);

        // Obtains the one (and only) application instance to ever be used.
        final CdiApplication instance = this.getJavaFxApplication();

        // Constructs the bean used to provide instances of the JavaFX application instance.
        JAVA_FX_APPLICATION_BEAN = new CdiApplicationBean(instance, beanAttributes, injectionTarget);

        abd.addBean(JAVA_FX_APPLICATION_BEAN);

    }

    void afterDeploymentValidation(final @Observes AfterDeploymentValidation adv, final BeanManager beanManager) {
        final CreationalContext<CdiApplication> creationalContext = beanManager.createCreationalContext(JAVA_FX_APPLICATION_BEAN);
        JAVA_FX_APPLICATION_BEAN.injectionTarget.inject(JAVA_FX_APPLICATION, creationalContext);
    }

}
