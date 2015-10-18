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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

/**
 * Created by Don on 18.10.2015.
 */
class BoardDatabaseHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "soundboard.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_BOARD = "boards";
    private static final String SQL_CREATE_TABLE_BOARDS =
            "CREATE TABLE " + Table.BOARDS + " (" +
                    Columns.ID + " INTEGER PRIMARY KEY," +
                    Columns.TITLE + " TEXT," +
                    Columns.DESCRIPTION + " TEXT )";
    private static final String SQL_CREATE_TABLE_SOUNDS =
            "CREATE TABLE " + Table.SOUNDS + " (" +
                    Columns.ID + " INTEGER PRIMARY KEY," +
                    Columns.BOARD_ID + " INTEGER," +
                    Columns.TITLE + " TEXT," +
                    Columns.IMAGE_PATH + " TEXT," +
                    Columns.SOUND_PATH + " TEXT )";

    public BoardDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Timber.d("onCreate()");
        db.execSQL(SQL_CREATE_TABLE_BOARDS);
        db.execSQL(SQL_CREATE_TABLE_SOUNDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Timber.d("onUpgrade()");

    }
}
