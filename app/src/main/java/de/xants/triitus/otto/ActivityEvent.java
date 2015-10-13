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
