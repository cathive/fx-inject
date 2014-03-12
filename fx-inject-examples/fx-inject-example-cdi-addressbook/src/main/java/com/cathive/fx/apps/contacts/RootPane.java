package com.cathive.fx.apps.contacts;

import com.cathive.fx.apps.contacts.model.Person;
import com.cathive.fx.inject.core.FXMLComponent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import javax.annotation.PostConstruct;

/**
 * @author Benjamin P. Jung
 */
@FXMLComponent(location = "RootPane.fxml", resources = "com.cathive.fx.apps.contacts.Messages")
public class RootPane extends AnchorPane {

    @FXML private ToolBar toolBar;
    @FXML private ListView<Person> contactListView;

    @PostConstruct
    protected void init() {

        final Person p1 = new Person();
        p1.setFirstName("John");
        p1.setLastName("Doe");

        final Person p2 = new Person();
        p2.setFirstName("Max");
        p2.setLastName("Mustermann");

        contactListView.getItems().add(p1);
        contactListView.getItems().add(p2);

    }

}
