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

package de.xants.triitus.viewholder;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.xants.triitus.R;
import de.xants.triitus.model.SoundEntry;

/**
 * Created by Don on 14.10.2015.
 */
public abstract class BaseSoundViewHolder extends LayoutViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    protected SoundEntry mSoundEntry;
    private ImageView mIvIcon;
    private TextView mTxtTitle;
    private boolean mIsOscillating = false;

    public BaseSoundViewHolder(@NonNull ViewGroup viewGroup, @LayoutRes int layoutRes) {
        super(viewGroup, layoutRes);
        this.mIvIcon = (ImageView) this.itemView.findViewById(R.id.ivCover);
        this.mTxtTitle = (TextView) this.itemView.findViewById(R.id.txtTitle);
        this.itemView.setOnClickListener(this);
        this.itemView.setOnLongClickListener(this);
    }

    protected ImageView getIcon() {
        return this.mIvIcon;
    }

    public final SoundEntry getSoundEntry() {
        return mSoundEntry;
    }

    @CallSuper
    public void setSoundEntry(@NonNull SoundEntry soundEntry) {
        mSoundEntry = soundEntry;
        this.mTxtTitle.setText(soundEntry.getTitle());
    }

    @Override
    public void onClick(View v) {
        if (v == itemView) {
            showOscillator(!this.mIsOscillating);
        }
    }

    public final void showOscillator(boolean show) {
        if (show == this.mIsOscillating) {
            return;
        }
        this.mIsOscillating = show;
        final ImageView ivOscillator = (ImageView) this.itemView.findViewById(R.id.ivOscillator);
        Drawable drawable = ivOscillator.getDrawable();
        if (drawable instanceof Animatable) {
            final Animatable animatable = (Animatable) drawable;
            if (this.mIsOscillating)
                animatable.start();
            else
                animatable.stop();
        }
        ivOscillator.setVisibility(this.mIsOscillating ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
