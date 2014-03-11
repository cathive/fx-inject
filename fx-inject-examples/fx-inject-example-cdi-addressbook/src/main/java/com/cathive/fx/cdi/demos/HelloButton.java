package com.cathive.fx.cdi.demos;

import com.cathive.fx.cdi.FXMLComponent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "HelloButton.fxml")
public class HelloButton extends Button implements Initializable {

    @Inject private Instance<FxDemoApplication> app;

    @FXML private String defaultTextString;

    private final BooleanProperty personalized = new SimpleBooleanProperty(this, "personalized");
    public boolean isPersonalized() { return this.personalized.get(); }
    public void setPersonalized(final boolean personalized) { this.personalized.set(personalized); }
    public BooleanProperty personalizedProperty() { return this.personalized; }

    private final StringProperty personalGreeting = new SimpleStringProperty(this, "personalGreeting");
    public String getPersonalGreeting() { return this.personalGreeting.get(); }
    public void setPersonalGreeting(final String personalGreeting) { this.personalGreeting.set(personalGreeting); }
    public StringProperty personalGreetingProperty() { return this.personalGreeting; }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        this.textProperty().bind(
                Bindings.when(personalized)
                        .then(personalGreeting)
                        .otherwise(defaultTextString));
    }git s

    @FXML
    protected void buttonPressed(final ActionEvent event) {
        this.setPersonalized(! this.isPersonalized());
        System.out.println(app.get());
    }

}
