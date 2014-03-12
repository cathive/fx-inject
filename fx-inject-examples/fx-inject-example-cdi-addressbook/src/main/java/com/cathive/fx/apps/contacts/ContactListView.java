package com.cathive.fx.apps.contacts;

import com.cathive.fx.apps.contacts.model.Person;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.enterprise.inject.spi.CDI;

/**
 * @author Benjamin P. Jung
 */
public class ContactListView extends ListView<Person> {

    public ContactListView() {
        super();
        this.setCellFactory(listView -> {
            final ListCell<Person> listCell = new ListCell<>();
            final ContactListCell graphics = CDI.current().select(ContactListCell.class).get();
            graphics.personProperty().bind(listCell.itemProperty());
            listCell.setGraphic(graphics);
            return listCell;
        });
    }

}
