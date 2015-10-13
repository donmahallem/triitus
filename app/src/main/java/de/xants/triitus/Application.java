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

package de.xants.triitus;

import de.xants.triitus.content.CM;
import timber.log.Timber;

/**
 * Created by Don on 11.10.2015.
 */
public final class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CM.init(this);
        Timber.plant(new Timber.DebugTree());
    }
}
