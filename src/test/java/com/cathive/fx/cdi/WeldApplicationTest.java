package com.cathive.fx.cdi;

import static org.testng.Assert.*;

import javafx.stage.Stage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import javax.enterprise.inject.spi.CDI;

/**
 * @author Benjamin P. Jung
 */
@Test
public class WeldApplicationTest {

    @Test
    public void testCdiContainerInitialization() throws Exception {

        assertNull(FxCdiExtension.getJavaFxApplication());

        final TestApplication app = new TestApplication();
        assertNull(FxCdiExtension.getJavaFxApplication());

        app.init();
        assertNotNull(FxCdiExtension.getJavaFxApplication());

    }

    public static class TestApplication extends WeldApplication {
        @Override
        public void start(Stage stage) throws Exception {
        }
    }

}
