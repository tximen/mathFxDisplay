package com.txi.math.mathfxdisplay.main;

import com.jfonux.controls.JFXHamburger;
import com.txi.math.mathfxdisplay.config.ContextHolder;
import com.txi.math.mathfxdisplay.function.FunctionPlotter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView implements  Initializable {

    private static final double LEFT_VISIBLE_WIDTH = 100d;

    @FXML
    private BorderPane rootPane;
    @FXML
    private VBox navigationBox;
    @FXML
    private StackPane displayStackContent;
    @FXML
    private JFXHamburger titleBurger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.titleBurger.selectedProperty().addListener(this::burgerSelected);
        burgerUpdate(true);
    }

    private void burgerSelected(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        burgerUpdate(newValue);
    }

    private void burgerUpdate(boolean selected) {
        if (this.titleBurger.selectedProperty().get() ^ selected) {
            this.titleBurger.setSelected(false);
        } else {
            navigationBox.setVisible(selected);
            navigationBox.getChildren().forEach(node -> node.setVisible(selected));
            updateComponentWidth(selected);
        }
    }

    private void updateComponentWidth(boolean selected) {
        if (selected) {
            updateWidth(navigationBox, new SimpleDoubleProperty(LEFT_VISIBLE_WIDTH)) ;
            updateWidth(displayStackContent, visibleWidthBinding());
        } else {
            updateWidth(navigationBox, new SimpleDoubleProperty(0d));
            updateWidth(displayStackContent, rootPane.widthProperty());
        }
    }

    private DoubleExpression visibleWidthBinding() {
        return Bindings.createDoubleBinding(this::calculateVisibleWidth, rootPane.widthProperty());
    }

    private double calculateVisibleWidth() {
        return  Math.max(0, rootPane.getWidth() - LEFT_VISIBLE_WIDTH);
    }


    private void updateWidth(Region node, DoubleExpression widthProperty) {
        node.maxWidthProperty().unbind();
        node.maxWidthProperty().bind(widthProperty);
        node.minWidthProperty().unbind();
        node.minWidthProperty().bind(widthProperty);
        node.prefWidthProperty().unbind();
        node.prefWidthProperty().bind(widthProperty);
    }

    @FXML
    public void openFunctionZeroCrossing() {
        burgerUpdate(false);

        displayStackContent.getChildren().clear();
        displayStackContent.getChildren().add(ContextHolder.getInstance().loadFxml("NullStellen"));
    }

    @FXML
    public void openSinus() {
        burgerUpdate(false);
        displayStackContent.getChildren().clear();
        FunctionPlotter plotter = new FunctionPlotter();
        plotter.widthProperty().bind(rootPane.widthProperty());
        plotter.heightProperty().bind(rootPane.heightProperty());
        plotter.draw();
        displayStackContent.getChildren().add(plotter);
    }
}
