package com.txi.math.mathfxdisplay.zerocrossing;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import javafx.fxml.Initializable;

import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import com.txi.math.mathfxdisplay.executor.TaskExecutor;

@Component
@Scope("prototype")
public class NullStellenView implements Initializable {


    @FXML
    private RowConstraints errorRow;
    @FXML
    private  Label errorLabel;
    @FXML
    private  Label errorMessage;
    @FXML
    private  HBox errorBox;
    @FXML
    private TextField fieldX0;

    @FXML
    private TextField fieldX1;

    @FXML
    private TextField fieldX2;

    @FXML
    private TextField functionField;

    @FXML
    private Label resultLabel;
    @FXML
    private RadioButton mullerBox;

    @FXML
    private Button resetButton;

    @FXML
    private Button exampleButton;

    @FXML
    private Button methodButton;

    @FXML
    private ListView<String> messageView;

    private final NullStellenModel viewModel;
    private final TaskExecutor taskExecutor;


    public NullStellenView(NullStellenModel viewModel, TaskExecutor taskExecutor) {
        this.viewModel = viewModel;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initError();
        this.resultLabel.setText("");
        this.viewModel.startX0Property().bind(this.fieldX0.textProperty());
        this.viewModel.startX1Property().bind(this.fieldX1.textProperty());
        this.viewModel.startX2Property().bind(this.fieldX2.textProperty());
        this.viewModel.functionProperty().bind(this.functionField.textProperty());
        this.messageView.itemsProperty().bind(this.viewModel.messagesProperty());
    }

    private void initError() {
        this.errorBox.visibleProperty().bind(this.viewModel.errorVisibleProperty());
        this.errorMessage.visibleProperty().bind(this.viewModel.errorVisibleProperty());
        this.errorLabel.visibleProperty().bind(this.viewModel.errorVisibleProperty());
        this.errorMessage.textProperty().bind(this.viewModel.errorMessageProperty());
        this.errorRow.maxHeightProperty().bind(this.viewModel.errorRowHeightProperty());
        this.errorRow.minHeightProperty().bind(this.viewModel.errorRowHeightProperty());
        this.errorRow.prefHeightProperty().bind(this.viewModel.errorRowHeightProperty());
    }

    @FXML
    void example() {
        this.functionField.setText("x^3 - 2 * x^2 - 5");
        this.fieldX0.setText("-1");
        this.fieldX1.setText("0");
        this.fieldX2.setText("1");
    }

    @FXML
    void executeMethod() {
        this.viewModel.resetError();
        this.taskExecutor.executeWithError(resultLabel.textProperty(), new MullerMethodTask(this.viewModel), this.viewModel);
    }

    @FXML
    void reset() {
        this.viewModel.resetError();
        this.functionField.setText("");
        this.fieldX0.setText("");
        this.fieldX1.setText("");
        this.fieldX2.setText("");
        this.resultLabel.setText("");
    }

    @FXML
    void copyToClipboard() {
        System.err.println("copy to clipboard");
    }
}
