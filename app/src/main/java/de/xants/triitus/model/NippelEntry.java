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

/**
 * Created by Don on 10.10.2015.
 */
public class NippelEntry {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("title")
    private String mTitle;
    @Expose
    @SerializedName("icon")
    private String mIcon;
    @Expose
    @SerializedName("sound")
    private String mSound;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getSound() {
        return mSound;
    }
}
