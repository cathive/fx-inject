package com.cathive.fx.cdi;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.function.Consumer;

import static com.cathive.fx.cdi.SimpleFXMLComponentTest.ComponentTestImpl.uiFinishedListener;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * Checks if a basic FXML component can be injected via CDI.
 *
 * @author Alexander Erben
 */
@Test
public class SimpleFXMLComponentTest {

    @Test
    public void testInit() throws Exception {
        assertNull(FxCdiExtension.getJavaFxApplication());
        uiFinishedListener = (Stage uut) -> {
            assertNotNull(FxCdiExtension.getJavaFxApplication());
            uut.hide();
        };
        Application.launch(ComponentTestImpl.class);
    }

    public static class ComponentTestImpl extends CdiApplication {
        public static Consumer<Stage> uiFinishedListener;

        @Inject
        SimpleFXMLComponent component;

        @Override
        public void start(final Stage stage) throws Exception {
            assertThat(component, CoreMatchers.notNullValue());
            assertThat(component.isHello(), is(true));
            component.setText("Test");
            stage.setScene(new Scene(component));
            stage.show();
            uiFinishedListener.accept(stage);
        }
    }
}