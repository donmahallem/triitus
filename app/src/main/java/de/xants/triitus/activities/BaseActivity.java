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

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.xants.triitus.content.CM;
import de.xants.triitus.otto.ActivityEvent;

/**
 * Created by Don on 10.10.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private final Object BUS_LISTENER = new Object() {
        @com.squareup.otto.Subscribe
        public void onStartActivityEvent(ActivityEvent event) {
            Intent intent = new Intent(BaseActivity.this, event.CLAZZ);
            if (event.EXTRAS != null)
                intent.putExtras(event.EXTRAS);
            if (Build.VERSION.SDK_INT >= 21) {
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(BaseActivity.this,
                                event.PAIRS);
                // start the new activity
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    };

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        Log.d("test3", "test3");
        CM.BUS().register(BUS_LISTENER);
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        CM.BUS().unregister(BUS_LISTENER);
    }
}
