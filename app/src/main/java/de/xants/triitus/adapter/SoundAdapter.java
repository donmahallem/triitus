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

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import de.xants.triitus.model.SoundEntry;
import de.xants.triitus.viewholder.BaseSoundViewHolder;
import de.xants.triitus.viewholder.CardSoundViewHolder;
import de.xants.triitus.viewholder.ListSoundViewHolder;

/**
 * Created by Don on 14.10.2015.
 */
public final class SoundAdapter extends RecyclerView.Adapter<BaseSoundViewHolder> {
    public final static int TYPE_LIST = 0, TYPE_CARD = 1;
    private SortedList.Callback<SoundEntry> SoundEntryListCallback = new SortedList.Callback<SoundEntry>() {
        @Override
        public int compare(SoundEntry o1, SoundEntry o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }

        @Override
        public void onInserted(int position, int count) {
            SoundAdapter.this.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            SoundAdapter.this.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            SoundAdapter.this.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            SoundAdapter.this.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(SoundEntry oldItem, SoundEntry newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(SoundEntry item1, SoundEntry item2) {
            return item1.getTitle().equals(item2.getTitle());
        }
    };
    private SortedList<SoundEntry> mSoundEntryList = new SortedList<SoundEntry>(SoundEntry.class, SoundEntryListCallback);
    private int mItemViewType = TYPE_CARD;

    @ViewType
    public final int getItemViewType() {
        return this.mItemViewType;
    }

    public final void setItemViewType(@ViewType int type) {
        if (this.mItemViewType == type)
            return;
        this.mItemViewType = type;
        this.notifyItemRangeChanged(0, this.mSoundEntryList.size());
    }

    public void addSound(@NonNull SoundEntry soundEntry) {
        this.mSoundEntryList.add(soundEntry);
    }

    @ViewType
    @Override
    public int getItemViewType(int position) {
        return this.mItemViewType;
    }

    @Override
    public BaseSoundViewHolder onCreateViewHolder(ViewGroup parent, @ViewType int viewType) {
        switch (viewType) {
            case TYPE_CARD:
                return new CardSoundViewHolder(parent);
            case TYPE_LIST:
                return new ListSoundViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseSoundViewHolder holder, int position) {
        holder.setSoundEntry(this.mSoundEntryList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mSoundEntryList.size();
    }

    public final synchronized void addSounds(@NonNull List<SoundEntry> soundEntryList) {
        if (soundEntryList.size() == 0)
            return;
        this.mSoundEntryList.beginBatchedUpdates();
        this.mSoundEntryList.addAll(soundEntryList);
        this.mSoundEntryList.endBatchedUpdates();
    }

    @IntDef({TYPE_LIST, TYPE_CARD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }
}
