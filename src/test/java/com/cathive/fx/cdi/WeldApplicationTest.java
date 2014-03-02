package com.cathive.fx.cdi;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

/**
 * Very basic unit test that checks if the application can start up and close correctly.
 * given a basic AnchorPane fxml on classpath
 *
 * @author Benjamin P. Jung
 * @author Alexander Erben (a_erben@outlook.com)
 */
@Test
public class WeldApplicationTest extends WeldApplication {

    private static Stage uut;
    private static final Lock lock = new ReentrantLock();
    private static final Condition isUIInitialized = lock.newCondition();
    private static final Condition hasTestFinished = lock.newCondition();

    @Inject
    @FXMLLoaderParams(location = "weldApplicationTest.fxml")
    private FXMLLoader fxmlLoader;

    @Test
    public void testInit() throws Exception {
        lock.lock();
        assertNull(FxCdiExtension.getJavaFxApplication());
        Executors.newCachedThreadPool().execute(Application::launch);
        try {
            isUIInitialized.await();
            ObservableList<Node> sceneContent = uut.getScene().getRoot().getChildrenUnmodifiable();
            Button firstButton = (Button) sceneContent.get(0);
            assertThat(firstButton.getText(), is("Weld App"));
        } catch (InterruptedException e) {
            fail("Could not execute testInit ");
        } finally {
            hasTestFinished.signal();
            lock.unlock();
        }
        assertNotNull(FxCdiExtension.getJavaFxApplication());
    }

    @Override
    public void start(final Stage stage) {
        lock.lock();
        try {
            AnchorPane pane = this.fxmlLoader.load();
            stage.setScene(new Scene(pane));
            uut = stage;
            uut.show();
            isUIInitialized.signalAll();
            hasTestFinished.await();
            uut.hide();
        } catch (IOException | InterruptedException e) {
            fail("Could not initialize JavaFX application");
        } finally {
            lock.unlock();
        }
    }
}