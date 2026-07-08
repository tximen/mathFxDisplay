package com.txi.math.mathfxdisplay.executor;


import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Level;


@Component
public class TaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutor.class);

    private final ObjectProperty<Task<?>> currentTask;
    private final BooleanProperty active;
    private final DoubleProperty progress;

    public TaskExecutor() {
        this.active = new SimpleBooleanProperty();
        this.progress = new SimpleDoubleProperty(0D);
        this.currentTask = new SimpleObjectProperty<>();
    }

    public <T> void executeWithError(Property<T> valueProperty, Task<T> task, EventHandler<WorkerStateEvent> failureHandler) {
        execute(valueProperty, this::handleSuccess, failureHandler, this::handleCanceled, task);
    }

    public <T> void executeWithError(Task<T> task, EventHandler<WorkerStateEvent> failureHandler) {
        execute(null, this::handleSuccess, failureHandler, this::handleCanceled, task);
    }


    public <T> void execute(Property<T> valueProperty, EventHandler<WorkerStateEvent> successHandler, EventHandler<WorkerStateEvent> failureHandler, EventHandler<WorkerStateEvent> cancelHandler, Task<T> task) {
        cancelTask();
        currentTask.set(task);
        TaskService<T> service = new TaskService<>(task);
        bindValueProperty(valueProperty, service);
        unbindActiveTask();
        this.active.bind(service.runningProperty());
        this.progress.unbind();
        this.progress.bind(service.progressProperty());
        service.setOnFailed(new TaskFinishedHandler(failureHandler));
        service.setOnSucceeded(new TaskFinishedHandler(successHandler));
        service.setOnCancelled(new TaskFinishedHandler(cancelHandler));
        service.start();
    }


    public void cancelTask() {
        Task<?> task = this.currentTask.get();
        if (task != null) {
            task.cancel(true);
            Platform.runLater(this::unbindActiveTask);
            this.currentTask.set(null);
        }
    }

    public class TaskFinishedHandler implements EventHandler<WorkerStateEvent> {

        private final EventHandler<WorkerStateEvent> eventHandler;

        private TaskFinishedHandler(EventHandler<WorkerStateEvent> eventHandler) {
            this.eventHandler = eventHandler;
        }

        @Override
        public void handle(WorkerStateEvent event) {
            taskFinished();
            this.eventHandler.handle(event);
        }

    }


    private static <T> void bindValueProperty(Property<T> valueProperty, TaskService<T> service) {
        if (valueProperty != null) {
            valueProperty.unbind();
            valueProperty.bind(service.valueProperty());
        }
    }

    private void taskFinished() {
        currentTask.set(null);
        Platform.runLater(this::unbindActiveTask);
        this.progress.unbind();
    }


    private void unbindActiveTask() {
        this.progress.unbind();
        this.progress.set(0d);
        if (this.active.isBound()) {
            int counter = 0;
            while (counter < 80 && this.active.get()) {
                ++counter;
                waitSomeTime();
            }
            this.active.unbind();

            if (this.active.get()) {
                this.active.set(false);
            }
        }
    }


    private void waitSomeTime() {
        try {
            Thread.sleep(25L);
        } catch (InterruptedException exception) {
            LOGGER.error("interrupted", exception);
        }
    }


    private void handleSuccess(WorkerStateEvent event) {
        LOGGER.info("task succeeded");
    }

    private void handleFailure(WorkerStateEvent event) {
        LOGGER.error( "service processing failed", event.getSource().getException());
    }

    private void handleCanceled(WorkerStateEvent event) {
        LOGGER.error("task was canceled");
    }
}
