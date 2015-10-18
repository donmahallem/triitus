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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;

import timber.log.Timber;

/**
 * Created by Don on 15.10.2015.
 */
public final class SoundBoardProvider extends ContentProvider {
    public final static String[] BOARD_COLUMNS = new String[]{Columns.ID,
            Columns.TITLE,
            Columns.DESCRIPTION,
            Columns.SOUNDS};
    public final static String[] SOUND_COLUMNS = new String[]{Columns.ID,
            Columns.TITLE,
            Columns.SOUND_PATH,
            Columns.IMAGE_PATH};
    final static String PROVIDER_NAME = "de.xants.triitus.provider";
    final static String PATH_BOARD = "boards";
    final static String PATH_BOARD_ID = "boards/#";
    final static String PATH_SOUND = "sounds";
    final static String PATH_SOUND_ID = "sounds/#";
    final static String PATH_BOARD_SOUND = "boards/#/sounds";
    final static String PATH_BOARD_SOUND_ID = "boards/#/sounds/#";
    final static String PATH_BOARD_DATA_IMG = "boards/*/data/img/*";
    final static String PATH_BOARD_DATA_SOUND = "boards/*/data/sound/*";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int TYPE_BOARD = 1;
    private final static int TYPE_BOARD_ID = 2;
    private final static int TYPE_SOUND = 3;
    private final static int TYPE_SOUND_ID = 4;
    private final static int TYPE_BOARD_SOUND = 5;
    private final static int TYPE_BOARD_SOUND_ID = 6;
    private final static int TYPE_BOARD_DATA_IMAGE = 7;
    private final static int TYPE_BOARD_DATA_SOUND = 8;
    private final static String MIME_TYPE_JPEG = "image/jpeg";
    private final static String MIME_TYPE_PNG = "image/png";
    private final static String MIME_TYPE_WAV = "audio/wav";
    private final static String MIME_TYPE_MP3 = "audio/mpeg";
    private final static String CURSOR_TYPE_BOARD_ITEM = "vnd.android.cursor.item/board";
    private final static String CURSOR_TYPE_BOARD_DIR = "vnd.android.cursor.dir/board";
    private final static String CURSOR_TYPE_SOUND_ITEM = "vnd.android.cursor.item/sound";
    private final static String CURSOR_TYPE_SOUND_DIR = "vnd.android.cursor.dir/sound";

    static {
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD, TYPE_BOARD);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_ID, TYPE_BOARD_ID);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_SOUND, TYPE_SOUND);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_SOUND_ID, TYPE_SOUND_ID);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_SOUND, TYPE_BOARD_SOUND);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_SOUND_ID, TYPE_BOARD_SOUND_ID);
    }

    private BoardDatabase mBoardDatabase;

    @Override
    public boolean onCreate() {
        this.mBoardDatabase = new BoardDatabase(this.getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD:
                return this.mBoardDatabase.queryBoard(selection, selectionArgs, sortOrder);
            case TYPE_BOARD_ID:
                final long boardId = Long.parseLong(uri.getLastPathSegment());
                return this.mBoardDatabase.queryBoard(Columns.ID + "=" + boardId, null, sortOrder);
            case TYPE_SOUND:
                return this.mBoardDatabase.querySound(selection, selectionArgs, sortOrder);
            case TYPE_SOUND_ID:
                final long soundId = Long.parseLong(uri.getLastPathSegment());
                return this.mBoardDatabase.querySound(Columns.ID + "=" + soundId, null, sortOrder);
            case TYPE_BOARD_SOUND:
                return null;
            case TYPE_BOARD_SOUND_ID:
                return null;
            default:
                return null;
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        Timber.d("openFile( %s , %s )", uri.toString(), mode);
        if (uri.getPathSegments().size() < 2) {
            throw new FileNotFoundException("Not found");
        }
        final String boardId = uri.getPathSegments().get(1);
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD_DATA_IMAGE:
                final String imgId = uri.getPathSegments().get(4);
                File imgFile = new File(CM.getSoundBoardDirectory(this.getContext()), boardId + "/img/" + imgId);
                if (imgFile.exists()) {
                    return ParcelFileDescriptor.open(imgFile, fileModeToParcelMode(mode));
                }
                throw new FileNotFoundException(uri.getPath());
            case TYPE_BOARD_DATA_SOUND:
                final String soundId = uri.getPathSegments().get(4);
                final File soundFile = new File(CM.getSoundBoardDirectory(this.getContext()), boardId + "/sound/" + soundId);
                if (soundFile.exists()) {
                    return ParcelFileDescriptor.open(soundFile, fileModeToParcelMode(mode));
                }
                throw new FileNotFoundException(uri.getPath());
            default:
                throw new FileNotFoundException("Unknown path");
        }
    }

    private final int fileModeToParcelMode(@NonNull String mode) {
        final boolean r = mode.contains("r");
        final boolean w = mode.contains("w");
        if (r && w) {
            return ParcelFileDescriptor.MODE_READ_WRITE;
        } else if (r) {
            return ParcelFileDescriptor.MODE_READ_ONLY;
        } else if (w) {
            return ParcelFileDescriptor.MODE_WRITE_ONLY;
        } else {
            throw new IllegalArgumentException("Unsupported mode");
        }
    }

    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        Timber.d("getStreamTypes(%s,%s)", uri.toString(), mimeTypeFilter);
        if (mimeTypeFilter.startsWith("image")
                || sUriMatcher.match(uri) == TYPE_BOARD_DATA_IMAGE) {
            return new String[]{MIME_TYPE_JPEG, MIME_TYPE_PNG};
        } else if (mimeTypeFilter.startsWith("audio")
                || sUriMatcher.match(uri) == TYPE_BOARD_DATA_SOUND) {
            return new String[]{MIME_TYPE_WAV, MIME_TYPE_MP3};
        } else
            return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Timber.d("getType(%s)", uri.toString());
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD:
                return CURSOR_TYPE_BOARD_DIR;
            case TYPE_SOUND:
                return CURSOR_TYPE_SOUND_DIR;
            case TYPE_SOUND_ID:
                return CURSOR_TYPE_SOUND_ITEM;
            case TYPE_BOARD_ID:
                return CURSOR_TYPE_BOARD_ITEM;
            case TYPE_BOARD_SOUND:
                return CURSOR_TYPE_SOUND_DIR;
            case TYPE_BOARD_SOUND_ID:
                return CURSOR_TYPE_SOUND_ITEM;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD:
                final long id = this.mBoardDatabase.insertBoard(values);
                return Uri.parse("");
            case TYPE_SOUND:
                return Uri.parse("");
            case TYPE_BOARD_SOUND:
                return this.insert(UriBuilder.getSoundUri(),
                        values);
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD:
                return this.mBoardDatabase.deleteBoard(selection, selectionArgs);
            case TYPE_BOARD_ID:
                final long boardId = Long.parseLong(uri.getLastPathSegment());
                return this.mBoardDatabase.deleteBoard(Columns.ID + "=" + boardId, null);
            case TYPE_SOUND:
                return this.mBoardDatabase.deleteBoard(selection, selectionArgs);
            case TYPE_SOUND_ID:
                final long soundId = Long.parseLong(uri.getLastPathSegment());
                return this.mBoardDatabase.deleteBoard(Columns.ID + "=" + soundId, null);
            default:
                return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD:
                return this.mBoardDatabase.updateBoard(values, selection, selectionArgs);
            case TYPE_BOARD_SOUND:
                return this.mBoardDatabase.updateSound(values, selection, selectionArgs);
            default:
                return -1;
        }
    }
}
