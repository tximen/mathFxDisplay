package com.txi.math.mathfxdisplay.main;

import com.jfonux.controls.JFXHamburger;
import com.txi.math.mathfxdisplay.config.ContextHolder;
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
        double paneWidth = rootPane.getWidth();
        if (selected) {
            updateWidth(navigationBox, LEFT_VISIBLE_WIDTH);
            updateWidth(displayStackContent, Math.max(0, paneWidth - LEFT_VISIBLE_WIDTH));
        } else {
            updateWidth(navigationBox, 0);
            updateWidth(displayStackContent, Math.max(0, paneWidth));
        }
    }

    private void updateWidth(Region node, double newWidth) {
        node.setMaxWidth(newWidth);
        node.setPrefWidth(newWidth);
        node.setMinWidth(newWidth);
    }

    @FXML
    public void openFunctionZeroCrossing() {
        burgerUpdate(false);

        displayStackContent.getChildren().clear();
        displayStackContent.getChildren().add(ContextHolder.getInstance().loadFxml("NullStellen"));
    }
}
