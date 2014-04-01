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
