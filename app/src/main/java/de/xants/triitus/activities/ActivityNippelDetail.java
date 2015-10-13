package de.xants.triitus.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.ImageView;

import de.xants.triitus.R;
import de.xants.triitus.content.CM;

/**
 * Created by Don on 10.10.2015.
 */
public class ActivityNippelDetail extends BaseActivity {

    private ImageView mIvCover;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_nippel_detail);
        this.mIvCover = (ImageView) this.findViewById(R.id.ivCover);
        this.mCollapsingToolbarLayout = (CollapsingToolbarLayout) this.findViewById(R.id.collapsingToolbar);
        this.mCollapsingToolbarLayout.setTitle("Title");
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        CM.PICASSO().load("http://i.imgur.com/aRBNTnYm.jpg").fit().into(this.mIvCover);
    }
}
