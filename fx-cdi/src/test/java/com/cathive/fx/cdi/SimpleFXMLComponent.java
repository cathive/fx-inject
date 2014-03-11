package com.cathive.fx.cdi;

import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

/**
 * Simple FXML component for testing purposes
 * @author Benjamin P. Jung
 */
@SuppressWarnings("UnusedDeclaration")
@FXMLComponent(location = "SimpleFXMLComponent.fxml")
public class SimpleFXMLComponent extends Button {
    private final BooleanProperty hello = new SimpleBooleanProperty(this, "hello");
    public boolean isHello() { return this.hello.get(); }
    public void setHello(final boolean hello) { this.hello.set(hello); }
    public BooleanProperty helloProperty() { return this.hello; }
}
