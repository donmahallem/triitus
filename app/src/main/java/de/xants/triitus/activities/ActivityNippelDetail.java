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
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import de.xants.triitus.R;
import de.xants.triitus.content.CM;

/**
 * Created by Don on 10.10.2015.
 */
public class ActivityNippelDetail extends BaseActivity {

    private ImageView mIvCover;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_nippel_detail);
        this.mIvCover = (ImageView) this.findViewById(R.id.ivCover);
        this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById(R.id.collapsingToolbar);
        this.mCollapsingToolbarLayout.setTitle("Title");
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        CM.PICASSO().load("http://i.imgur.com/aRBNTnYm.jpg").fit().into(this.mIvCover);
    }
}
