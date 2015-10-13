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
