package com.ciandt.worldwonders.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by andersonr on 25/08/15.
 */
public abstract class BaseRepository<T> {
    protected Context context;
    private List<AsyncTask> tasks;

    public BaseRepository(Context context) {
        this.context = context;
        this.tasks = new ArrayList<>();
    }

    public void cancel() {
        for (AsyncTask asyncTask: tasks) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
    }

    protected void execute(Executor executor) {
        AsyncTask asyncTask = executor.createTask();
        tasks.add(asyncTask);
        asyncTask.execute();
    }

    protected void execute(Executor executor, int param) {
        AsyncTask asyncTask = executor.createTask();
        tasks.add(asyncTask);
        asyncTask.execute(param);
    }

    protected abstract class ObjectExecutor<T, P> implements Executor {
        ObjectResultListener<T> resultListener;

        public ObjectExecutor(ObjectResultListener<T> resultListener) {
            this.resultListener = resultListener;
        }

        public AsyncTask<P, Void, T> createTask() {
            return new AsyncTask<P, Void, T>() {

                @Override
                protected T doInBackground(P... params) {
                    return executeInBackground(params);
                }

                @Override
                protected void onPostExecute(T object) {
                    super.onPostExecute(object);
                    resultListener.onResult(null, object);
                    tasks.remove(this);
                }

            };
        }

        abstract T executeInBackground(P[] params);
    }

    protected abstract class ListExecutor<T, P> implements Executor {
        ListResultListener<T> resultListener;

        public ListExecutor(ListResultListener<T> resultListener) {
            this.resultListener = resultListener;
        }

        public AsyncTask<P, Void, List<T>> createTask() {
            return new AsyncTask<P, Void, List<T>>() {

                @Override
                protected List<T> doInBackground(P... params) {
                    return executeInBackground(params);
                }

                @Override
                protected void onPostExecute(List<T> object) {
                    super.onPostExecute(object);
                    resultListener.onResult(null, object);
                    tasks.remove(this);
                }

            };
        }

        abstract List<T> executeInBackground(P[] params);
    }

    protected interface Executor {
        AsyncTask createTask();
    }

    public interface ObjectResultListener<T> {
        void onResult(Exception e, T object);
    }
    public interface ListResultListener<T> {
        void onResult(Exception e, List<T> list);
    }
}
