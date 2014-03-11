package com.cathive.fx.cdi.demos;

import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Simple hello button. Note how the FXML-file defining this component is linked via the
 * <link>com.cathive.fx.inject.core.FXMLComponent</link> annotation.
 * Neat, isn't it?
 *
 * @author Benjamin P. Jung
 * @author Alexander Erben
 */
@SuppressWarnings("UnusedDeclaration")
@FXMLComponent(location = "HelloButton.fxml")
public class HelloButton extends Button implements Initializable {

    @Inject
    private Instance<FxDemoApplication> app;

    @FXML
    private String defaultTextString;

    private final BooleanProperty personalized = new SimpleBooleanProperty(this, "personalized");

    private final StringProperty personalGreeting = new SimpleStringProperty(this, "personalGreeting");

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.textProperty().bind(
                Bindings.when(personalized)
                        .then(personalGreeting)
                        .otherwise(defaultTextString));
    }

    @FXML
    protected void buttonPressed(final ActionEvent event) {
        this.setPersonalized(!this.isPersonalized());
        System.out.println(app.get());
    }

    public boolean isPersonalized() {
        return this.personalized.get();
    }

    public void setPersonalized(final boolean personalized) {
        this.personalized.set(personalized);
    }

    public BooleanProperty personalizedProperty() {
        return this.personalized;
    }

    public String getPersonalGreeting() {
        return this.personalGreeting.get();
    }

    public void setPersonalGreeting(final String personalGreeting) {
        this.personalGreeting.set(personalGreeting);
    }

    public StringProperty personalGreetingProperty() {
        return this.personalGreeting;
    }

}
