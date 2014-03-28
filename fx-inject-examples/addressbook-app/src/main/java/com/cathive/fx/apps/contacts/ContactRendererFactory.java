package com.cathive.fx.apps.contacts;

import com.cathive.fx.apps.contacts.model.*;
import javafx.beans.binding.StringBinding;
import javafx.scene.image.Image;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.beans.binding.Bindings.createStringBinding;

/**
 * This factory class is responsible for rendering a bunch of aspects concerning
 * the different contact types.
 * @author Benjamin P. Jung
 */
@Named
@Singleton
public class ContactRendererFactory {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(ContactRendererFactory.class.getName());

    private Image defaultPersonImage;
    private Image defaultFamilyImage;
    private Image defaultCompanyImage;

    public ContactRendererFactory() {
        super();
    }

    @PostConstruct
    protected void initialize() {
        this.defaultPersonImage = new Image(this.getClass().getResourceAsStream("/com/cathive/fx/apps/contacts/icons/avatar-default.png"));
        this.defaultFamilyImage = new Image(this.getClass().getResourceAsStream("/com/cathive/fx/apps/contacts/icons/system-users.png"));
        this.defaultCompanyImage = new Image(this.getClass().getResourceAsStream("/com/cathive/fx/apps/contacts/icons/avatar-default.png"));
    }

    public StringBinding createDisplayNameBinding(final Contact contact) {
        final ContactType type = contact.getType();
        final StringBinding binding;
        switch(type) {
            case PERSON:
                final Person person = (Person) contact;
                binding = createStringBinding(() ->
                    String.format("%s, %s", person.getLastName(), person.getFirstName()),
                        person.firstNameProperty(), person.lastNameProperty());
                break;
            case COMPANY:
                final Company company = (Company) contact;
                binding = createStringBinding(() -> company.getName(), company.nameProperty());
                break;
            case FAMILY:
                final Family family = (Family) contact;
                binding = createStringBinding(() -> family.getFamilyName(), family.familyNameProperty());
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown contact type: %s", type.name()));
        }
        return binding;
    }

    public Image getDefaultPhoto(final Contact contact) {
        final ContactType type = contact.getType();
        final Image image;
        switch (type) {
            case PERSON:
                image = defaultPersonImage;
                break;
            case COMPANY:
                image = defaultCompanyImage;
                break;
            case FAMILY:
                image = defaultFamilyImage;
                break;
            default:
                LOGGER.log(Level.WARNING, "Couldn't determine default photo for contact type: {0}", type.name());
                image = null;
                break;
        }
        return image;
    }

}
