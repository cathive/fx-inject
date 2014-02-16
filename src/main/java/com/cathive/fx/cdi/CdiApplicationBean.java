package com.cathive.fx.cdi;

import javafx.application.Application;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.*;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple wrapper class that helps us to get things up and running.
 * @author Benjamin P. Jung
 */
public class CdiApplicationBean<T extends CdiApplication> implements Bean<T>, PassivationCapable {

    final T instance;
    final Set<Annotation> qualifiers;

    final BeanAttributes<T> beanAttributes;
    final InjectionTarget<T> injectionTarget;

    public CdiApplicationBean(final T instance, final BeanAttributes<T> beanAttributes, final InjectionTarget<T> injectionTarget) {

        super();

        this.instance = instance;

        this.qualifiers = new HashSet<Annotation>();
        this.qualifiers.add(new AnnotationLiteral<Any>() {});
        this.qualifiers.add(new AnnotationLiteral<Default>() {});

        this.beanAttributes = beanAttributes;
        this.injectionTarget = injectionTarget;

    }

    @Override
    public Class<?> getBeanClass() {
        return instance.getClass();
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
        return ApplicationScoped.class;
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
