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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.util.AnnotationLiteral;

import javafx.application.Application;
import javafx.application.Platform;

/**
 * This JavaFX extension makes sure that sub-classes of {@link com.cathive.fx.cdi.CdiApplication} can be
 * injected (using the {@link javax.inject.Inject @javax.inject.Inject} annotation).
 * @author Benjamin P. Jung
 */
@SuppressWarnings({ "UnusedDeclaration", "CdiManagedBeanInconsistencyInspection" })
class JavaFXExtension implements Extension {

    /** Logger for this instance. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * The JavaFX application instance to be provided by the CDI BeanManager.
     * <p>It is crucial that this field is populated (using {@link #setJavaFxApplication(CdiApplication)}
     * prior to initializing the CDI context.</p>
     */
    static ThreadLocal<CdiApplication> JAVA_FX_APPLICATION = new ThreadLocal<>();

    private CdiApplicationBean<CdiApplication> JAVA_FX_APPLICATION_BEAN;

    /**
     * Sets the JavaFX application instance to be provided by the CDI BeanManager.
     * @param javaFxApplication
     *         The JavaFX application instance to be provided by the CDI BeanManager.
     */
    public static void setJavaFxApplication(final CdiApplication javaFxApplication) {

        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            JAVA_FX_APPLICATION.set(javaFxApplication);
            latch.countDown();
        });

        if (!Platform.isFxApplicationThread()) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    public static <T extends CdiApplication> T getJavaFxApplication() {

        final ValueLatch<CdiApplication> latch = new ValueLatch(1);
        Platform.runLater(() -> {
            latch.setValue(JAVA_FX_APPLICATION.get());
            latch.countDown();
        });
        if (!Platform.isFxApplicationThread()) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        return (T) latch.getValue();
    }

    // ---- ==== CDI LIFECYCLE EVENTS ==== -----------------------------------------------------------------------------

    void beforeBeanDiscovery(@Observes final BeforeBeanDiscovery beforeBeanDiscovery, final BeanManager beanManager) {

        if (getJavaFxApplication() == null) {
            throw new IllegalStateException("JavaFX application was not provided. Did you call FxCdiExtension.setJavaFxApplication(..)?");
        }

    }

    void processAnnotatedType(@Observes final ProcessAnnotatedType<? extends Application> pat, final BeanManager beanManager) {
        final Class<? extends Application> javaClass = pat.getAnnotatedType().getJavaClass();
        if (javaClass.equals(getJavaFxApplication().getClass())) {
            // We veto any sub-classes of CdiApplication to make sure, that the right bean
            // will be pulled into the dependency graph when using @Inject annotations.
            pat.veto();
        }
    }

    <X> void processInjectionTarget(@Observes final ProcessInjectionTarget<X> pit, final BeanManager beanManager) {
        final Class<X> javaClass = pit.getAnnotatedType().getJavaClass();
        if (javaClass.equals(getJavaFxApplication().getClass())) {

        }
    }

    <T, X> void processInjectionPoint(@Observes final ProcessInjectionPoint<T, X> pip, final BeanManager beanManager) {
    }

    void afterBeanDiscovery(@Observes final AfterBeanDiscovery abd, final BeanManager beanManager) {

        assert JAVA_FX_APPLICATION_BEAN == null;

        final AnnotatedType<CdiApplication> annotatedType = (AnnotatedType<CdiApplication>) beanManager.createAnnotatedType(getJavaFxApplication().getClass());
        final BeanAttributes<CdiApplication> beanAttributes = beanManager.createBeanAttributes(annotatedType);
        final InjectionTarget<CdiApplication> injectionTarget = beanManager.createInjectionTarget(annotatedType);

        // Obtains the one (and only) application instance to ever be used.
        final CdiApplication instance = this.getJavaFxApplication();

        // Constructs the bean used to provide instances of the JavaFX application instance.
        JAVA_FX_APPLICATION_BEAN = new CdiApplicationBean(instance, beanAttributes, injectionTarget);

        abd.addBean(JAVA_FX_APPLICATION_BEAN);

    }

    void afterDeploymentValidation(@Observes final  AfterDeploymentValidation adv, final BeanManager beanManager) {
        final CreationalContext<CdiApplication> creationalContext = beanManager.createCreationalContext(JAVA_FX_APPLICATION_BEAN);
        JAVA_FX_APPLICATION_BEAN.injectionTarget.inject(getJavaFxApplication(), creationalContext);
    }

    void beforeShutdown(@Observes final BeforeShutdown beforeShutdown, final BeanManager beanManager) {
        this.JAVA_FX_APPLICATION_BEAN = null;
    }


    /**
     * A simple wrapper class that helps us to get things up and running.
     * @author Benjamin P. Jung
     */
    static class CdiApplicationBean<T extends CdiApplication> implements Bean<T>, PassivationCapable {

        /** The scope to be used for our JavaFX application instance. */
        static final Class<? extends Annotation> SCOPE = Dependent.class;

        final T instance;
        final Set<Annotation> qualifiers;

        final BeanAttributes<T> beanAttributes;
        final InjectionTarget<T> injectionTarget;

        public CdiApplicationBean(final T instance, final BeanAttributes<T> beanAttributes, final InjectionTarget<T> injectionTarget) {

            super();

            this.instance = instance;

            this.qualifiers = new HashSet<>();
            this.qualifiers.add(new AnnotationLiteral<Any>() {});
            this.qualifiers.add(new AnnotationLiteral<Default>() {});

            this.beanAttributes = beanAttributes;
            this.injectionTarget = injectionTarget;

        }

        @Override
        public Class<?> getBeanClass() {
            return this.instance.getClass();
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            return this.injectionTarget.getInjectionPoints();
        }

        @Override
        public boolean isNullable() {
            return false;
        }

        @Override
        public Set<Type> getTypes() {
            return this.beanAttributes.getTypes();
        }

        @Override
        public Set<Annotation> getQualifiers() {
            return this.qualifiers;
        }

        @Override
        public Class<? extends Annotation> getScope() {
            return SCOPE;
        }

        @Override
        public String getName() {
            return this.beanAttributes.getName();
        }

        @Override
        public Set<Class<? extends Annotation>> getStereotypes() {
            return this.beanAttributes.getStereotypes();
        }

        @Override
        public boolean isAlternative() {
            return this.beanAttributes.isAlternative();
        }

        @Override
        public T create(CreationalContext<T> creationalContext) {
            return this.instance;
        }

        @Override
        public void destroy(T instance, CreationalContext<T> creationalContext) {
            // TODO Should we call instance.stop() here? I actually don't think so...
        }

        @Override
        public String getId() {
            return this.instance.getClass().getName();
        }
    }

    /**
     * A simple countdown latch that can carry a value
     * @param <T>
     *     Type of the value to be carried.
     */
    private static class ValueLatch<T> extends CountDownLatch {

        /**
         * Constructs a {@code CountDownLatch} initialized with the given count.
         *
         * @param count the number of times {@link #countDown} must be invoked
         *              before threads can pass through {@link #await}
         * @throws IllegalArgumentException if {@code count} is negative
         */
        public ValueLatch(final int count) {
            super(count);
        }

        private T value;
        public T getValue() { return this.value; }
        public void setValue(final T value) { this.value = value; }

    }

}
