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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Random;

import de.xants.triitus.R;
import de.xants.triitus.adapter.NippelAdapter;
import de.xants.triitus.model.Nippel;

/**
 * Created by Don on 10.10.2015.
 */
public class ActivityNippelList extends BaseActivity {


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
        this.mCoordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.coordinatorLayout);

        //Setup RecyclerView
        this.mNippelAdapter = new NippelAdapter();
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this,
                this.getResources().getInteger(R.integer.card_large_columns)));
        this.mRecyclerView.setAdapter(this.mNippelAdapter);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Nippel nippel = new Nippel();
            nippel.setTitle(new StringBuilder().append(random.nextInt()).toString());
            nippel.setId("" + random.nextLong());
            switch (random.nextInt(4)) {
                case 0:
                    nippel.setImage("http://i.imgur.com/jNNBvoSm.jpg");
                    break;
                case 1:
                    nippel.setImage("http://i.imgur.com/r7VZAubm.jpg");
                    break;
                case 2:
                    nippel.setImage("http://i.imgur.com/7sgSLZ3m.png");
                    break;
                case 3:
                    nippel.setImage("http://i.imgur.com/aRBNTnYm.jpg");
                    break;
            }
            this.mNippelAdapter.addNippel(nippel);
        }
    }


}
