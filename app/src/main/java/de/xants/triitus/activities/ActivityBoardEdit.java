package de.xants.triitus.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import de.xants.triitus.R;

/**
 * Created by Don on 11.10.2015.
 */
public final class ActivityBoardEdit extends BaseActivity {
    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_board_edit);
    }
}
