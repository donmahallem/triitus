/*
 * Copyright (C) 2015 https://github.com/donmahallem
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.xants.triitus.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Random;

import de.xants.triitus.R;
import de.xants.triitus.adapter.NippelAdapter;
import de.xants.triitus.content.Columns;
import de.xants.triitus.content.NippelLoader;
import de.xants.triitus.content.UriBuilder;
import de.xants.triitus.model.SoundBoard;
import rx.Observer;
import timber.log.Timber;

/**
 * Created by Don on 10.10.2015.
 */
public class ActivityBoardList extends BaseActivity implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CoordinatorLayout mCoordinatorLayout;
    private NippelAdapter mNippelAdapter;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_nippel_list);
        this.mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        this.mFloatingActionButton = (FloatingActionButton) this.findViewById(R.id.floatingActionButton);
        this.mFloatingActionButton.setOnClickListener(this);
        this.mCoordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.coordinatorLayout);

        //Setup RecyclerView
        this.mNippelAdapter = new NippelAdapter();
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this,
                this.getResources().getInteger(R.integer.card_large_columns)));
        this.mRecyclerView.setAdapter(this.mNippelAdapter);

    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        NippelLoader.getInstalledNippel(this).subscribe(new Observer<SoundBoard>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(SoundBoard soundBoard) {
                ActivityBoardList.this.mNippelAdapter.addNippel(soundBoard);
            }
        });
        Cursor cursor = getContentResolver().query(UriBuilder.getBoardUri(),
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Timber.d("Row: " + cursor.getString(cursor.getColumnIndex(Columns.TITLE)));
            cursor.moveToNext();
        }
        cursor.close();
    }


    @Override
    public void onClick(View v) {
        if (v == this.mFloatingActionButton) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Columns.TITLE, "Title " + new Random().nextInt(10000));
            contentValues.put(Columns.DESCRIPTION, "Description " + new Random().nextInt(10000));
            Timber.d("LOLOL: " + getContentResolver().insert(UriBuilder.getBoardUri(), contentValues).toString());
        }
    }
}
