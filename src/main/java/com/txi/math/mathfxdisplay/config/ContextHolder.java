package com.txi.math.mathfxdisplay.config;


import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;

public class ContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextHolder.class);

    private static final ContextHolder SOLE_INSTANCE = new ContextHolder();

    private String[] arguments;
    private ConfigurableApplicationContext applicationContext;

    private ContextHolder() {
    }

    public static ContextHolder getInstance() {
        return ContextHolder.SOLE_INSTANCE;
    }


    public <T> T loadFxml(String name) {
        try (InputStream inputStream = loadResource(name).openStream()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            return fxmlLoader.load(inputStream);
        } catch (IOException exception) {
            LOGGER.error("cannot load resource {}", name, exception);
            throw new UncheckedIOException("cannot load resource: " + name,  exception);
        }
    }

    private URL loadResource(String name) {
        String resourceName = "/fxml/%sView.fxml".formatted(name);
        LOGGER.info("load {}", resourceName);
        URL resource = ContextHolder.class.getResource(resourceName);
        if (resource == null) {
            LOGGER.error("no such resource {}", resourceName);
            throw new IllegalStateException("no such resource %s".formatted(resourceName));
        } else {
            return resource;
        }
    }

    public void shutdown() {
        SpringApplication.exit(this.applicationContext);
        System.exit(0);
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
