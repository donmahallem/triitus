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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import de.xants.triitus.content.CM;

/**
 * Created by Don on 10.10.2015.
 */
public class SoundBoard {
    @Since(1.0)
    @Expose
    @SerializedName("id")
    private String mId;
    @Since(1.0)
    @Expose
    @SerializedName("title")
    private String mTitle;
    @Since(1.0)
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Since(1.0)
    @Expose
    @SerializedName("version")
    private int mVersion;
    @Since(1.0)
    @Expose
    @SerializedName("cover")
    private String mImage;
    @Since(1.0)
    @Expose
    @SerializedName("entries")
    private List<SoundEntry> mSoundEntryList;

    public static SoundBoard loadFromFile(InputStream inputStream) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream);
            return CM.GSON().fromJson(reader, SoundBoard.class);
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static SoundBoard loadFromFile(File file) throws IOException {
        return loadFromFile(new FileInputStream(file));
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getVersion() {
        return mVersion;
    }

    public void setVersion(int version) {
        mVersion = version;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getDescription() {
        return mDescription;
    }

    public List<SoundEntry> getSoundEntryList() {
        return Collections.unmodifiableList(mSoundEntryList);
    }

    public void storeToFile(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Error storing SoundBoard");
        }
    }

    @Override
    public String toString() {
        return "SoundBoard{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
