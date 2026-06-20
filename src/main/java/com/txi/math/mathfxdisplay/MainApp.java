package com.txi.math.mathfxdisplay;

import com.txi.math.mathfxdisplay.config.ContextHolder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class MainApp extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);


    private Scene appScene;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(this.appScene);
        primaryStage.setOnCloseRequest(_ -> {
            primaryStage.close();
            ContextHolder.getInstance().shutdown();
        });
        primaryStage.show();
    }

    @Override
    public void init() {
        ContextHolder contextHolder = ContextHolder.getInstance();
        contextHolder.setApplicationContext(SpringApplication.run(MathSpringBootApplication.class, contextHolder.getArguments()));
        this.appScene = new Scene(contextHolder.loadFxml("Main"), 1100, 500);
    }

}
