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

package de.xants.triitus.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Don on 18.10.2015.
 */
final class BoardDatabase {

    private BoardDatabaseHelper mDatabaseHelper;

    public BoardDatabase(@NonNull Context context) {
        this.mDatabaseHelper = new BoardDatabaseHelper(context);
    }

    private static long insertBoard(@NonNull SQLiteDatabase sqLiteDatabase,
                                    @NonNull ContentValues contentValues) {
        return sqLiteDatabase.insert(Table.BOARDS, null, contentValues);
    }

    private static long insertSound(@NonNull SQLiteDatabase sqLiteDatabase,
                                    @NonNull ContentValues contentValues) {
        return sqLiteDatabase.insert(Table.SOUNDS, null, contentValues);
    }

    private static int deleteBoard(@NonNull SQLiteDatabase sqLiteDatabase,
                                   @IntRange(from = 0) long id) {
        return sqLiteDatabase.delete(Table.BOARDS, Columns.ID + "=" + id, null);
    }

    private static int deleteBoard(@NonNull SQLiteDatabase sqLiteDatabase,
                                   @Nullable String selection,
                                   @Nullable String[] whereArgs) {
        return sqLiteDatabase.delete(Table.BOARDS,
                selection,
                whereArgs);
    }

    private static int deleteSound(@NonNull SQLiteDatabase sqLiteDatabase,
                                   @Nullable String selection,
                                   @Nullable String[] whereArgs) {
        return sqLiteDatabase.delete(Table.SOUNDS,
                selection,
                whereArgs);
    }

    @IntRange(from = 0)
    private static int updateBoard(@NonNull SQLiteDatabase sqLiteDatabase,
                                   @NonNull ContentValues contentValues,
                                   @NonNull String selection,
                                   @Nullable String[] selectionArgs) {
        return sqLiteDatabase.update(Table.BOARDS, contentValues, selection, selectionArgs);
    }

    @IntRange(from = 0)
    private static int updateSound(@NonNull SQLiteDatabase sqLiteDatabase,
                                   @NonNull ContentValues contentValues,
                                   @NonNull String selection,
                                   @Nullable String[] selectionArgs) {
        return sqLiteDatabase.update(Table.SOUNDS, contentValues, selection, selectionArgs);
    }

    public long insertBoard(@NonNull ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final long result = BoardDatabase.insertBoard(sqLiteDatabase, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public long insertSound(@NonNull ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final long result = BoardDatabase.insertSound(sqLiteDatabase, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public final void close() {
        this.mDatabaseHelper.close();
    }

    @IntRange(from = 0)
    public int deleteBoard(@Nullable String selection,
                           @Nullable String[] whereArgs) {
        final SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final int deleted = BoardDatabase.deleteBoard(sqLiteDatabase, selection, whereArgs);
        sqLiteDatabase.close();
        return deleted;
    }

    @IntRange(from = 0)
    public int deleteSound(@Nullable String selection,
                           @Nullable String[] whereArgs) {
        final SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final int deleted = BoardDatabase.deleteSound(sqLiteDatabase, selection, whereArgs);
        sqLiteDatabase.close();
        return deleted;
    }

    @IntRange(from = 0)
    public int updateBoard(@NonNull ContentValues values,
                           @NonNull String selection,
                           @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final int updated = BoardDatabase.updateBoard(sqLiteDatabase,
                values,
                selection,
                selectionArgs);
        sqLiteDatabase.close();
        return updated;
    }

    @IntRange(from = 0)
    public int updateSound(@NonNull ContentValues values,
                           @NonNull String selection,
                           @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        final int updated = BoardDatabase.updateSound(sqLiteDatabase,
                values,
                selection,
                selectionArgs);
        sqLiteDatabase.close();
        return updated;
    }

    public Cursor querySound(String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public Cursor queryBoard(String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
}
