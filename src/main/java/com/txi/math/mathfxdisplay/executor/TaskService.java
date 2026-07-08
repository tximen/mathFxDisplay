package com.txi.math.mathfxdisplay.executor;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TaskService<T> extends Service<T> {

    private final Task<T> task;

    public TaskService(Task<T> task) {
        this.task = task;
    }

    @Override
    protected Task<T> createTask() {
        return this.task;
    }

}
