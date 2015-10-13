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
