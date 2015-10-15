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

package de.xants.triitus.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import de.xants.triitus.R;
import de.xants.triitus.content.NippelLoader;
import de.xants.triitus.model.SoundBoard;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by Don on 11.10.2015.
 */
public final class ActivityBoardInstall extends BaseActivity implements View.OnClickListener {
    private final static int STATE_LOADING = 0, STATE_ERROR = 1, STATE_LOADED = 2;
    private Button mBtnOk, mBtnCancel;
    private TextView mTxtTitle, mTxtDescription, mTxtEntries, mTxtErrorMessage;
    private ContentLoadingProgressBar mLoadingProgressBar;
    private SoundBoard mSoundBoard = null;
    private int mState = STATE_LOADING;
    private GridLayout mGridLayout;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_board_install);
        this.mBtnCancel = (Button) this.findViewById(R.id.btnCancel);
        this.mBtnOk = (Button) this.findViewById(R.id.btnOk);
        this.mBtnOk.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
        this.mGridLayout = (GridLayout) this.findViewById(R.id.gridLayout);
        this.mLoadingProgressBar = (ContentLoadingProgressBar) this.findViewById(R.id.contentLoadingProgressBar);
        this.mTxtDescription = (TextView) this.findViewById(R.id.txtDescription);
        this.mTxtTitle = (TextView) this.findViewById(R.id.txtTitle);
        this.mTxtEntries = (TextView) this.findViewById(R.id.txtEntries);
    }

    @ActivityState
    public final int getState() {
        return this.mState;
    }

    private final void setState(@ActivityState int state) {
        switch (state) {
            case STATE_LOADING:
                this.mBtnCancel.setEnabled(false);
                this.mBtnOk.setEnabled(false);
                this.mLoadingProgressBar.show();
                this.mGridLayout.setVisibility(View.GONE);
                this.mTxtErrorMessage.setVisibility(View.GONE);
                break;
            case STATE_LOADED:
                this.mBtnCancel.setEnabled(true);
                this.mBtnOk.setEnabled(true);
                this.mLoadingProgressBar.hide();
                this.mGridLayout.setVisibility(View.VISIBLE);
                this.mTxtErrorMessage.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                this.mBtnCancel.setEnabled(true);
                this.mBtnOk.setEnabled(false);
                this.mLoadingProgressBar.hide();
                this.mGridLayout.setVisibility(View.GONE);
                this.mTxtErrorMessage.setVisibility(View.VISIBLE);
                break;
        }
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        this.setState(STATE_LOADING);
        NippelLoader.peakBoardInformation(this, this.getIntent()).subscribe(new Subscriber<SoundBoard>() {

            @Override
            public void onStart() {
                Timber.d("onStart()");
                ActivityBoardInstall.this.setState(STATE_LOADING);
            }

            @Override
            public void onCompleted() {
                Timber.d("onCompleted()");
                ActivityBoardInstall.this.setState(STATE_LOADED);
            }

            @Override
            public void onError(Throwable e) {
                ActivityBoardInstall.this.setState(STATE_ERROR);
            }

            @Override
            public void onNext(SoundBoard soundBoard) {
                Timber.d("onNext()");
                ActivityBoardInstall.this.setSoundBoard(soundBoard);
            }
        });
    }

    private void setSoundBoard(@NonNull SoundBoard soundBoard) {
        this.mSoundBoard = soundBoard;
        this.mTxtTitle.setText(soundBoard.getTitle());
        this.mTxtDescription.setText(soundBoard.getDescription());
        this.mTxtEntries.setText(soundBoard.getSoundEntryList().size());
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        if (v == this.mBtnCancel) {

        } else if (v == this.mBtnOk) {
        }
    }

    @IntDef({STATE_ERROR, STATE_LOADED, STATE_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityState {
    }
}
