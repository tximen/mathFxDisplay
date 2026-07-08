package com.txi.math.mathfxdisplay.zerocrossing;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.apache.commons.numbers.complex.Complex;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class NullStellenModel implements EventHandler<WorkerStateEvent> {

    private final StringProperty startX0;
    private final StringProperty startX1;
    private final StringProperty startX2;

    private final StringProperty function;
    private final BooleanProperty errorVisible;
    private final DoubleProperty errorRowHeight;
    private final StringProperty errorMessage;
    private final ObjectProperty<ObservableList<String>> messages;


    public NullStellenModel() {
        this.startX0 = new SimpleStringProperty();
        this.startX1 = new SimpleStringProperty();
        this.startX2 = new SimpleStringProperty();

        this.function = new SimpleStringProperty();
        this.errorVisible  = new SimpleBooleanProperty();
        this.errorMessage  = new SimpleStringProperty();
        this.messages  = new SimpleObjectProperty<>();
        this.errorRowHeight = new SimpleDoubleProperty(0d);
    }

    public String getStartX0() {
        return startX0.get();
    }

    public StringProperty startX0Property() {
        return startX0;
    }

    public String getStartX1() {
        return startX1.get();
    }

    public StringProperty startX1Property() {
        return startX1;
    }

    public String getStartX2() {
        return startX2.get();
    }

    public StringProperty startX2Property() {
        return startX2;
    }



    public String getFunction() {
        return function.get();
    }

    public StringProperty functionProperty() {
        return function;
    }

    public ObservableList<String> getMessages() {
        return messages.get();
    }

    public BooleanProperty errorVisibleProperty() {
        return errorVisible;
    }



    public DoubleProperty errorRowHeightProperty() {
        return errorRowHeight;
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public ObjectProperty<ObservableList<String>> messagesProperty() {
        return messages;
    }

    public void addMessage(String message) {
        Platform.runLater(() -> this.messages.get().add(message));
    }


    public void resetError() {
        this.errorVisible.setValue(false);
        this.errorRowHeight.setValue(0d);
        this.errorMessage.set("");
        this.messages.set(FXCollections.observableArrayList());
    }

    @Override
    public void handle(WorkerStateEvent event) {
        if (event.getSource().getException() != null) {
            Platform.runLater(()-> handleError(event.getSource().getException()));
        }
    }



    private void handleError(Throwable exception) {
        this.errorRowHeight.set(30d);
        this.errorVisible.set(true);
        this.errorMessage.set(exception.getMessage());
        this.messages.set(FXCollections.observableArrayList(convertStackTrace(exception)));
    }

    private  List<String>  convertStackTrace(Throwable exception) {
        List<String> traceMessages = new ArrayList<>();
        int length = exception.getStackTrace().length;
        for (int i = 0; i < length; i++) {
            traceMessages.add(convert(i, exception.getStackTrace()[i]));
        }
        return traceMessages;
    }

    private String convert(int index, StackTraceElement element) {
        StringBuilder builder = new StringBuilder();
        if (index > 0) {
            builder.append("     ");
        }
        builder.append(element.getClassName());
        builder.append(".");
        builder.append(element.getMethodName());
        builder.append(":");
        builder.append(element.getLineNumber());
        return builder.toString();
    }
}
