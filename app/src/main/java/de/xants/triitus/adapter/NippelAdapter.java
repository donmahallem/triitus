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

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import de.xants.triitus.model.Nippel;

/**
 * Created by Don on 10.10.2015.
 */
public final class NippelAdapter extends RecyclerView.Adapter<NippelViewHolder> {

    private final static int TYPE_NIPPEL = 0;
    private SortedList.Callback<Nippel> NippelListCallback = new SortedList.Callback<Nippel>() {
        @Override
        public int compare(Nippel o1, Nippel o2) {
            return o2.getTitle().compareTo(o1.getTitle());
        }

        @Override
        public void onInserted(int position, int count) {
            NippelAdapter.this.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            NippelAdapter.this.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            NippelAdapter.this.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            NippelAdapter.this.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Nippel oldItem, Nippel newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Nippel item1, Nippel item2) {
            return item1.getId().equals(item2.getId());
        }
    };
    private SortedList<Nippel> mNippelSortedList = new SortedList<Nippel>(Nippel.class, NippelListCallback);

    @Override
    public int getItemViewType(int position) {
        return TYPE_NIPPEL;
    }

    @Override
    public NippelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NIPPEL:
                return new NippelViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unknown viewtype provided");
        }
    }

    @Override
    public void onBindViewHolder(NippelViewHolder holder, int position) {
        holder.setData(this.mNippelSortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mNippelSortedList.size();
    }

    public void addNippel(Nippel nippel) {
        this.mNippelSortedList.add(nippel);
    }
}
