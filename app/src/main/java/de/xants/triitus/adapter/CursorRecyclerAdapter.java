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

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import de.xants.triitus.content.Columns;

/**
 * Created by Don on 19.10.2015.
 */
public abstract class CursorRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final String INDEX_ID;
    private boolean mDataValid;
    private Cursor mCursor;
    private DataSetObserver mDataSetObserver;
    private int mIndexColumn;

    public CursorRecyclerAdapter(@Nullable Cursor cursor) {
        this(cursor, Columns.ID);
    }

    public CursorRecyclerAdapter(@Nullable Cursor cursor,
                                 @NonNull String indexId) {
        this.INDEX_ID = indexId;
        this.mCursor = cursor;
        this.mDataValid = cursor != null;
        this.mIndexColumn = mDataValid ? mCursor.getColumnIndex(INDEX_ID) : -1;
        this.mDataSetObserver = new NotifyingDataSetObserver();
        if (this.mCursor != null) {
            this.mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    public final void setCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #setCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     */
    @Nullable
    public final Cursor swapCursor(Cursor newCursor) {
        if (newCursor == this.mCursor) {
            return null;
        }
        final Cursor oldCursor = this.mCursor;
        if (oldCursor != null && this.mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mCursor = newCursor;
        if (this.mCursor != null) {
            if (this.mDataSetObserver != null) {
                this.mCursor.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mIndexColumn = newCursor.getColumnIndexOrThrow(INDEX_ID);
            this.mDataValid = true;
            this.notifyDataSetChanged();
        } else {
            this.mIndexColumn = -1;
            this.mDataValid = false;
            this.notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
        return oldCursor;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public abstract void onBindViewHolder(@NonNull VH viewHolder,
                                          @NonNull Cursor cursor);

    @IntRange(from = 0)
    @Override
    public int getItemCount() {
        if (this.mDataValid &&
                this.mCursor != null) {
            return this.mCursor.getCount();
        }
        return 0;
    }

    @IntRange(from = 0)
    @Override
    public long getItemId(@IntRange(from = 0) int position) {
        if (this.mDataValid &&
                this.mCursor != null &&
                this.mCursor.moveToPosition(position)) {
            return this.mCursor.getLong(this.mIndexColumn);
        }
        return 0;
    }

    @Override
    public final void onBindViewHolder(@NonNull VH viewHolder,
                                       @IntRange(from = 0) int position) {
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!this.mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        this.onBindViewHolder(viewHolder, this.mCursor);
    }

    private final class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            CursorRecyclerAdapter.this.mDataValid = true;
            CursorRecyclerAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            CursorRecyclerAdapter.this.mDataValid = false;
            CursorRecyclerAdapter.this.notifyDataSetChanged();
        }
    }
}
