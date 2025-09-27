package com.txi.math.mathfxdisplay.zerocrossing;

import javafx.fxml.Initializable;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class NullStellenView implements Initializable {

    private final NullStellenModel viewModel;

    public NullStellenView(NullStellenModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
