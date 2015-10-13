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

package de.xants.triitus.otto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;

/**
 * Created by Don on 10.10.2015.
 */
public final class ActivityEvent<T extends Activity> {
    public final Class<T> CLAZZ;
    public final Pair<View, String>[] PAIRS;
    public final Bundle EXTRAS;

    private ActivityEvent(Class<T> clazz, Bundle arguments, Pair<View, String>... pairs) {
        this.CLAZZ = clazz;
        this.EXTRAS = arguments;
        this.PAIRS = pairs;
    }

    public static <T extends Activity> ActivityEvent create(@NonNull Class<T> clazz,
                                                            @Nullable Bundle extras,
                                                            @Nullable Pair<View, String>... pairs) {
        return new ActivityEvent(clazz, extras, pairs);
    }

}
