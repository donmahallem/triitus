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

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import de.xants.triitus.R;
import de.xants.triitus.content.CM;
import de.xants.triitus.model.SoundEntry;

/**
 * Created by Don on 14.10.2015.
 */
public final class CardSoundViewHolder extends BaseSoundViewHolder {

    public CardSoundViewHolder(@NonNull ViewGroup viewGroup) {
        super(viewGroup, R.layout.vh_sound_card);
    }

    @CallSuper
    public void setSoundEntry(@NonNull SoundEntry soundEntry) {
        super.setSoundEntry(soundEntry);
        CM.PICASSO().load("http://i.imgur.com/aRBNTnYm.jpg").fit().into(getIcon());
    }

}
