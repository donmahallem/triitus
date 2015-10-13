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
