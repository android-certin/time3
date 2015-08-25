package com.ciandt.worldwonders.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.ciandt.worldwonders.database.Dao;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class WondersRepository {

    private Context context;
    private List<AsyncTask> tasks;

    public WondersRepository(Context context) {
        this.context = context;
        this.tasks = new ArrayList<>();
    }

    @Nonnull
    public void getAll(final WonderAllListener wonderAllListener) {

        AsyncTask<Void, Void, List<Wonder>> asyncTask = new AsyncTask<Void, Void, List<Wonder>>() {

            @Override
            protected List<Wonder> doInBackground(Void... params) {

                Dao<Wonder> dao = new WonderDao(context);
                List<Wonder> result = dao.getAll();
                dao.close();

                return result;
            }

            @Override
            protected void onPostExecute(List<Wonder> wonders) {
                super.onPostExecute(wonders);
                wonderAllListener.onWonderAll(null, wonders);
                tasks.remove(this);
            }

        };

        tasks.add(asyncTask);
        asyncTask.execute();

    }

    public interface WonderAllListener {
        void onWonderAll(Exception e, List<Wonder> wonders);
    }

    public void cancel() {
        for (AsyncTask asyncTask: tasks) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
    }
}
