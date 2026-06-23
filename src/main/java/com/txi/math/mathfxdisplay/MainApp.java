package com.txi.math.mathfxdisplay;

import com.txi.math.mathfxdisplay.config.ContextHolder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;

import java.net.URL;

public class MainApp extends Application {

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
        loadStyle();
    }

    private void loadStyle() {
        URL styleSheet = MainApp.class.getResource("/style/app-component.css");
        if (styleSheet != null) {
            this.appScene.getStylesheets().add(styleSheet.toExternalForm());
        }
    }

}
