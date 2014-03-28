package com.cathive.fx.apps.contacts.model.converter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Benjamin P. Jung
 */
@Converter
public class ImageConverter implements AttributeConverter<Image, byte[]> {

    /** Logger for this instance. */
    private static final Logger LOGGER = Logger.getLogger(ImageConverter.class.getName());

    @Override
    public byte[] convertToDatabaseColumn(final Image image) {
        byte[] bytes;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024)) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
            bytes = baos.toByteArray();
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Couldn't convert image to byte array");
            bytes = null;
        }
        return bytes;
    }

    @Override
    public Image convertToEntityAttribute(final byte[] bytes) {
        Image img;
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            img = new Image(bis);
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Couldn't create image from byte array.");
            img = null;
        }
        return img;
    }

}
