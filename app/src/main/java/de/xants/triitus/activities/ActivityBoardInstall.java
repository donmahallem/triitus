package de.xants.triitus.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.xants.triitus.R;
import de.xants.triitus.content.NippelLoader;
import de.xants.triitus.model.Nippel;
import rx.Subscriber;

/**
 * Created by Don on 11.10.2015.
 */
public final class ActivityBoardInstall extends BaseActivity implements View.OnClickListener {
    private Button mBtnOk, mBtnCancel;
    private TextView mTxtTitle, mTxtDescription, mTxtEntries;
    private ContentLoadingProgressBar mLoadingProgressBar;
    private Nippel mNippel = null;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_board_install);
        this.mBtnCancel = (Button) this.findViewById(R.id.btnCancel);
        this.mBtnOk = (Button) this.findViewById(R.id.btnOk);
        this.mBtnOk.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
        this.mLoadingProgressBar = (ContentLoadingProgressBar) this.findViewById(R.id.contentLoadingProgressBar);
        this.mTxtDescription = (TextView) this.findViewById(R.id.txtDescription);
        this.mTxtTitle = (TextView) this.findViewById(R.id.txtTitle);
        this.mTxtEntries = (TextView) this.findViewById(R.id.txtEntries);
    }

    private void setLoading(boolean loading) {
        if (loading) {
            this.mLoadingProgressBar.show();
        } else {
            this.mLoadingProgressBar.hide();
        }
        this.mBtnCancel.setEnabled(!loading);
        this.mBtnOk.setEnabled(!loading);
    }

    @Override
    public void onResume() {
        super.onResume();
        NippelLoader.peakBoardInformation(this, this.getIntent()).subscribe(new Subscriber<Nippel>() {

            @Override
            public void onStart() {
                ActivityBoardInstall.this.setLoading(true);
            }

            @Override
            public void onCompleted() {
                ActivityBoardInstall.this.setLoading(false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Nippel nippel) {
                ActivityBoardInstall.this.setNippel(nippel);
            }
        });
    }

    private void setNippel(@NonNull Nippel nippel) {
        this.mNippel = nippel;
        this.mTxtTitle.setText(nippel.getTitle());
        this.mTxtDescription.setText(nippel.getDescription());
        this.mTxtEntries.setText(nippel.getNippelEntryList().size());
    }

    @Override
    public void onClick(View v) {
        if (v == this.mBtnCancel) {

        } else if (v == this.mBtnOk) {
        }
    }
}
