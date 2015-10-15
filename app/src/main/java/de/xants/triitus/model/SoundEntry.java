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

package de.xants.triitus.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

/**
 * Created by Don on 10.10.2015.
 */
public class SoundEntry {
    @Since(1.0)
    @Expose
    @SerializedName("id")
    private int mId;
    @Since(1.0)
    @Expose
    @SerializedName("title")
    private String mTitle;
    @Since(1.0)
    @Expose
    @SerializedName("icon")
    private String mIcon;
    @Since(1.0)
    @Expose
    @SerializedName("sound")
    private String mSound;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getSound() {
        return mSound;
    }
}
