package com.ciandt.worldwonders.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.ciandt.worldwonders.database.Dao;
import com.ciandt.worldwonders.database.WonderDao;
import com.ciandt.worldwonders.models.Wonder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class WondersRepository extends BaseRepository<Wonder> {

    public WondersRepository(Context context) {
        super(context);
    }

    @Nonnull
    public void getAll(final ListResultListener<Wonder> resultListener) {
        ListExecutor<Wonder, Void> executor = new ListExecutor(resultListener) {
            @Override
            List<Wonder> executeInBackground(Object[] params) {
                Dao<Wonder> dao = new WonderDao(context);
                List<Wonder> result = dao.getAll();
                dao.close();

                return result;
            }
        };

        execute(executor);
    }

    @Nonnull
    public void getRandom(int count, final ListResultListener resultListener) {
        ListExecutor<Wonder, Integer> executor = new ListExecutor(resultListener) {
            @Override
            List<Wonder> executeInBackground(Object[] params) {
                WonderDao dao = new WonderDao(context);
                List<Wonder> result = dao.getRandom((Integer)params[0]);
                dao.close();

                return result;
            }
        };

        execute(executor, count);
    }
}
