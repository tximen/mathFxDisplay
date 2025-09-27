package com.txi.math.mathfxdisplay.main;

import com.jfonux.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView implements  Initializable {

    private final MainModel viewModel;


    @FXML
    private VBox navigationBox;
    @FXML
    private StackPane displayStackContent;
    @FXML
    private JFXHamburger titleBurger;

    public MainView(MainModel viewModel) {
        this.viewModel = viewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.navigationBox.visibleProperty().bind(this.titleBurger.selectedProperty());
    }

    @FXML
    public void openFunctionZeroCrossing() {
        displayStackContent.getChildren().clear();
        displayStackContent.getChildren().add(new Label("Null Stellen"));
        System.out.println("zerocrossing");
    }
}
