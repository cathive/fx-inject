package com.cathive.fx.apps.contacts;

import com.cathive.fx.apps.contacts.model.Person;
import com.cathive.fx.inject.core.FXMLComponent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.New;

/**
 * @author Benjamin P. Jung
 */
@Dependent
@FXMLComponent(location = "ContactListCell.fxml")
public class ContactListCell extends HBox {

    public static final String PERSON_PROPERTY = "person";

    private final ObjectProperty<Person> person = new SimpleObjectProperty<>(this, PERSON_PROPERTY);
    public ObjectProperty<Person> personProperty() { return this.person; }
    public Person getPerson() { return this.person.get(); }

    @FXML private Label displayNameLabel;
    @FXML private ImageView contactNameLabel;

    @PostConstruct
    protected void init() {
        this.displayNameLabel.textProperty().bind(
                Bindings.createObjectBinding(
                        () -> this.getPerson() == null ? "" : this.getPerson().getDisplayName(),
                        this.person));
    }


}
