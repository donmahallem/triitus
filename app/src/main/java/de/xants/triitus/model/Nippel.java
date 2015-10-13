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
public class Nippel {
    @Expose
    @SerializedName("id")
    private String mId;
    @Expose
    @SerializedName("title")
    private String mTitle;
    @Expose
    @SerializedName("description")
    private String mDescription;
    @Expose
    @SerializedName("version")
    private int mVersion;
    @Expose
    @SerializedName("cover")
    private String mImage;
    @Expose
    @SerializedName("entries")
    private List<NippelEntry> mNippelEntryList;

    public static Nippel loadFromFile(InputStream inputStream) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream);
            return CM.GSON().fromJson(reader, Nippel.class);
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static Nippel loadFromFile(File file) throws IOException {
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

    public List<NippelEntry> getNippelEntryList() {
        return Collections.unmodifiableList(mNippelEntryList);
    }

    public void storeToFile(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Error storing Nippel");
        }
    }

    @Override
    public String toString() {
        return "Nippel{" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
