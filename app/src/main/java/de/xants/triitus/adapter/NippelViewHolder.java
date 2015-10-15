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

package de.xants.triitus.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.xants.triitus.R;
import de.xants.triitus.activities.ActivityNippelDetail;
import de.xants.triitus.content.CM;
import de.xants.triitus.model.SoundBoard;
import de.xants.triitus.otto.ActivityEvent;
import de.xants.triitus.viewholder.LayoutViewHolder;

/**
 * Created by Don on 10.10.2015.
 */
final class NippelViewHolder extends LayoutViewHolder implements View.OnClickListener {

    private SoundBoard mSoundBoard = null;
    private TextView mTxtTitle;
    private ImageView mIvCover;

    public NippelViewHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.vh_nippel);
        this.mTxtTitle = (TextView) this.itemView.findViewById(R.id.txtTitle);
        this.mIvCover = (ImageView) this.itemView.findViewById(R.id.ivCover);
        this.itemView.setOnClickListener(this);
    }

    public void setData(@NonNull SoundBoard soundBoard) {
        this.mSoundBoard = soundBoard;
        this.mTxtTitle.setText(soundBoard.getTitle());
        CM.PICASSO().cancelRequest(this.mIvCover);
        CM.PICASSO().load(soundBoard.getImage()).fit().into(this.mIvCover);
    }

    @Override
    public void onClick(View v) {
        if (v == this.itemView) {
            Log.d("test", "test");
            Bundle bundle = new Bundle();
            bundle.putString("id", this.mSoundBoard.getId());
            CM.BUS().post(ActivityEvent
                    .create(ActivityNippelDetail.class,
                            bundle,
                            Pair.create((View) this.mIvCover, "cover"),
                            Pair.create((View) this.mTxtTitle, "title")));
        }
    }
}
