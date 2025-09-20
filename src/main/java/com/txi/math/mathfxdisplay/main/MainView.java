package com.txi.math.mathfxdisplay.main;

import com.jfonux.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        System.out.println("zerocrossing");
    }
}
