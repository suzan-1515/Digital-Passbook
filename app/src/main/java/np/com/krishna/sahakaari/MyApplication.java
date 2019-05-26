package np.com.krishna.sahakaari;

import android.app.Application;

import np.com.krishna.BuildConfig;
import timber.log.Timber;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
