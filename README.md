fx-cdi
======

[![Build Status](https://travis-ci.org/cathive/fx-cdi.png)](https://travis-ci.org/cathive/fx-cdi)

Introduction
------------

This library provides some useful classes that help you to use
CDI alongside / within your JavaFX applications.

A compiled and ready-to-use version of this library can be found in the
Sonatype OSS Maven Repository (oss.sonatype.org). To use the library
in your Maven based projects just add the following lines to your
'pom.xml':

```xml
<dependency>
  <groupId>com.cathive.fx</groupId>
  <artifactId>fx-cdi</artifactId>
  <version>${fx-cdi.version}</version>
</dependency>
```

Features
--------

Below you'll find a list of already existing features as well as features that are planned for
future releases:

- [x] Support for JavaFX applications backed by JBoss WELD SE
- [x] Instances of your JavaFX applications can be injected (using javax.inject.Inject annotation)
      wherever you want
- [x] Easy FXMLLoader configuration (using the @FXMLLoaderParams annotation)

Example Usage
-------------

The Java code below shows how to use fx-cdi in conjunction with JBoss WELD SE. (Make sure that you have the
required JAR files in your classpath):

```java
import javax.inject.Inject;
import javafx.fxml.FXMLLoader;
import static javafx.stage.Stage;
import com.cathive.fx.cdi.WeldApplication;
import com.cathive.fx.cdi.FXMLLoaderParams;

public class MyApplication extends WeldApplication {

    @Inject
    @FXMLLoaderParams(resources = "my.example.app.Stage.fxml")
    private FXMLLoader fxmlLoader;

    public void start(final Stage stage) throws java.lang.Exception {

        // ...
        // Use the FXMLLoader instance to load your stuff.
        // If you have declared a fx:controller attribute inside your FXML file, the
        // fx-cdi extension will make sure that the controller class will be constructed
        // using JBoss WELD. You can mix @Inject and @FXML annotations inside your
        // controller class as you like and both of them will work just fine.
        // ...

    }
}
```