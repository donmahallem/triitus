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

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import de.xants.triitus.R;
import de.xants.triitus.adapter.SoundAdapter;
import de.xants.triitus.content.CM;
import de.xants.triitus.content.NippelLoader;
import de.xants.triitus.model.SoundBoard;
import rx.Observer;

/**
 * Created by Don on 10.10.2015.
 */
public class ActivityNippelDetail extends BaseActivity {

    private final static String KEY_LAYOUT = "layout";
    private ImageView mIvCover;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private SoundAdapter mSoundAdapter;
    private Toolbar mToolbar;
    private SoundBoard mSoundBoard = null;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_nippel_detail);
        this.mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.mIvCover = (ImageView) this.findViewById(R.id.ivCover);
        this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById(R.id.collapsingToolbar);
        this.mCollapsingToolbarLayout.setTitle("Title");
        this.setSupportActionBar(this.mToolbar);
        this.mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        this.mSoundAdapter = new SoundAdapter();
        if (savedInstanceState != null) {
            final int value = savedInstanceState.getInt(KEY_LAYOUT);
            this.mSoundAdapter
                    .setItemViewType((value == SoundAdapter.TYPE_LIST)
                            ? SoundAdapter.TYPE_LIST : SoundAdapter.TYPE_CARD);
        }
        this.mRecyclerView.setLayoutManager(getLayoutManagerForType(this.mSoundAdapter.getItemViewType()));
        this.mRecyclerView.setAdapter(this.mSoundAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                switchLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final RecyclerView.LayoutManager getLayoutManagerForType(@SoundAdapter.ViewType int type) {
        switch (type) {
            case SoundAdapter.TYPE_CARD:
                return new GridLayoutManager(this, 2);
            case SoundAdapter.TYPE_LIST:
                return new LinearLayoutManager(this);
            default:
                return null;
        }
    }

    private void switchLayout() {
        final int currentLayout = this.mSoundAdapter.getItemViewType();
        if (currentLayout == SoundAdapter.TYPE_CARD) {
            this.mSoundAdapter.setItemViewType(SoundAdapter.TYPE_LIST);
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            this.mSoundAdapter.setItemViewType(SoundAdapter.TYPE_CARD);
            this.mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_LAYOUT, this.mSoundAdapter.getItemViewType());
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        NippelLoader.getNippel(this, "de.xants.triitus.cena").subscribe(new Observer<SoundBoard>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(SoundBoard soundBoard) {
                ActivityNippelDetail.this.onSoundBoardLoaded(soundBoard);
            }
        });
        CM.PICASSO().load("http://i.imgur.com/aRBNTnYm.jpg").fit().into(this.mIvCover);
    }

    private void onSoundBoardLoaded(@NonNull SoundBoard soundBoard) {
        this.mSoundBoard = soundBoard;
        this.mSoundAdapter.addSounds(soundBoard.getSoundEntryList());
    }
}
