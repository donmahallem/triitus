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
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.xants.triitus.model.SoundBoard;
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
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int TYPE_BOARD_LIST = 1;
    private final static int TYPE_BOARD = 2;
    private final static int TYPE_BOARD_SOUND_LIST = 3;
    private final static int TYPE_BOARD_SOUND = 4;
    private final static int TYPE_BOARD_DATA_IMAGE = 5;
    private final static int TYPE_BOARD_DATA_SOUND = 6;
    private final static String PROVIDER_NAME = "de.xants.triitus.provider";
    private final static String PATH_BOARD_LIST = "boards";
    private final static String PATH_BOARD = "boards/*";
    private final static String PATH_BOARD_SOUND_LIST = "boards/*/sounds";
    private final static String PATH_BOARD_SOUND = "boards/*/sounds/#";
    private final static String PATH_BOARD_DATA_IMG = "boards/*/data/img/*";
    private final static String PATH_BOARD_DATA_SOUND = "boards/*/data/sound/*";
    private final static String MIME_TYPE_JPEG = "image/jpeg";
    private final static String MIME_TYPE_PNG = "image/png";
    private final static String MIME_TYPE_WAV = "audio/wav";
    private final static String MIME_TYPE_MP3 = "audio/mpeg";
    private final static String CURSOR_TYPE_BOARD_ITEM = "vnd.android.cursor.item/board";
    private final static String CURSOR_TYPE_BOARD_DIR = "vnd.android.cursor.dir/board";
    private final static String CURSOR_TYPE_SOUND_ITEM = "vnd.android.cursor.item/sound";
    private final static String CURSOR_TYPE_SOUND_DIR = "vnd.android.cursor.dir/sound";

    static {
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_LIST, TYPE_BOARD_LIST);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD, TYPE_BOARD);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_SOUND_LIST, TYPE_BOARD_SOUND_LIST);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_SOUND, TYPE_BOARD_SOUND);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_DATA_IMG, TYPE_BOARD_DATA_IMAGE);
        sUriMatcher.addURI(PROVIDER_NAME, PATH_BOARD_DATA_SOUND, TYPE_BOARD_DATA_SOUND);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final File boardFolder = CM.getSoundBoardDirectory(this.getContext());
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD_LIST:
                final MatrixCursor boardListCursor = new MatrixCursor(BOARD_COLUMNS);
                for (File dir : boardFolder.listFiles()) {
                    if (dir.isDirectory()) {
                        final File manifest = new File(dir, "manifest.json");
                        try {
                            SoundBoard soundBoard = SoundBoard.loadFromFile(manifest);
                            if (soundBoard != null) {
                                boardListCursor.newRow()
                                        .add(Columns.ID, soundBoard.getId())
                                        .add(Columns.DESCRIPTION, soundBoard.getDescription())
                                        .add(Columns.TITLE, soundBoard.getTitle())
                                        .add(Columns.SOUNDS, soundBoard.getSoundEntryList().size());
                            }
                        } catch (IOException e) {

                        }
                    }
                }
                return boardListCursor;
            case TYPE_BOARD:
                final MatrixCursor cursor = new MatrixCursor(BOARD_COLUMNS);
                final String id = uri.getPathSegments().get(1);
                final File manifest = new File(boardFolder, id + "/manifest.json");
                try {
                    SoundBoard soundBoard = SoundBoard.loadFromFile(manifest);
                    if (soundBoard != null) {
                        cursor.newRow()
                                .add(Columns.ID, soundBoard.getId())
                                .add(Columns.DESCRIPTION, soundBoard.getDescription())
                                .add(Columns.TITLE, soundBoard.getTitle())
                                .add(Columns.SOUNDS, soundBoard.getSoundEntryList().size());
                    }
                } catch (IOException e) {

                }
                return cursor;
            default:
                return null;
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        Timber.d("openFile( %s , %s )", uri.toString(), mode);
        switch (sUriMatcher.match(uri)) {
            case TYPE_BOARD_DATA_IMAGE:
                final String boardId = uri.getPathSegments().get(1);
                final String imgId = uri.getPathSegments().get(4);
                File file = new File(CM.getSoundBoardDirectory(this.getContext()), boardId + "/img/" + imgId);
                if (file.exists()) {
                    return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
                }
                throw new FileNotFoundException(uri.getPath());
            case TYPE_BOARD_DATA_SOUND:
                throw new FileNotFoundException("Not yet implemented");
            default:
                throw new FileNotFoundException("Unknown path");
        }
    }

    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        if (mimeTypeFilter.startsWith("image") || sUriMatcher.match(uri) == TYPE_BOARD_DATA_IMAGE) {
            return new String[]{MIME_TYPE_JPEG, MIME_TYPE_PNG};
        } else if (mimeTypeFilter.startsWith("audio") || sUriMatcher.match(uri) == TYPE_BOARD_DATA_SOUND) {
            return new String[]{MIME_TYPE_WAV, MIME_TYPE_MP3};
        } else
            return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final List pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 0) {
            return null;
        } else if (pathSegments.get(0).equals("boards")) {
            if (pathSegments.size() == 1) {
                return CURSOR_TYPE_BOARD_DIR;
            } else if (pathSegments.size() == 2) {
                return CURSOR_TYPE_BOARD_ITEM;
            } else if (pathSegments.get(2).equals("sounds")) {
                if (pathSegments.size() == 3) {
                    return CURSOR_TYPE_SOUND_DIR;
                } else if (pathSegments.size() == 4) {
                    return CURSOR_TYPE_SOUND_ITEM;
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
