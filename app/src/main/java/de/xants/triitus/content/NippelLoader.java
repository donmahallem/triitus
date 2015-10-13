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


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.xants.triitus.model.Nippel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Don on 11.10.2015.
 */
public final class NippelLoader {

    private final static String BOARDS = "boards";


    public static File getSoundBoardDirectory(@NonNull final Context context) {
        final File file = new File(context.getExternalFilesDir(null), BOARDS);
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public static Observable<Nippel> getInstalledNippel(@NonNull final Context context) {
        return getInstalledNippel(getSoundBoardDirectory(context));
    }

    public static Observable<Nippel> getInstalledNippel(final File installedDir) {
        return Observable.create(
                new Observable.OnSubscribe<Nippel>() {
                    @Override
                    public void call(Subscriber<? super Nippel> sub) {
                        if (sub.isUnsubscribed())
                            return;
                        for (File file : installedDir.listFiles()) {
                            if (!file.isDirectory()) {
                                continue;
                            }
                            final File manifest = new File(file, "manifest.json");
                            if (manifest.exists()) {
                                try {
                                    sub.onNext(Nippel.loadFromFile(file));
                                } catch (Exception exception) {
                                    sub.onError(exception);
                                }
                            }
                        }
                        sub.onCompleted();
                    }
                }
        );
    }

    public static Observable<Nippel> peakBoardInformation(final Context context, final Intent intent) {
        return Observable.create(
                new Observable.OnSubscribe<Nippel>() {
                    @Override
                    public void call(Subscriber<? super Nippel> sub) {
                        if (sub.isUnsubscribed())
                            return;
                        try {
                            ZipFile zipFile = new ZipFile(NippelLoader.readBoardSource(context, intent));
                            Enumeration<? extends ZipEntry> entries = zipFile.entries();
                            while (entries.hasMoreElements()) {
                                ZipEntry zipEntry = entries.nextElement();
                                Timber.d("Zip entry found: " + zipEntry.getName());
                                if (zipEntry.getName().equals("manifest.json")) {
                                    Timber.d("manifest found: " + zipEntry.getName());
                                    sub.onNext(Nippel.loadFromFile(zipFile.getInputStream(zipEntry)));
                                    break;
                                }
                            }/*
                            while(zipFile.entries().hasMoreElements()){
                                final ZipEntry zipEntry=zipFile.entries().nextElement();
                                Timber.d("Zip entry found: "+zipEntry.getName());
                                if(zipEntry.getName().equals("manifest.json")){
                                    Timber.d("manifest found: "+zipEntry.getName());
                                    sub.onNext(Nippel.loadFromFile(zipFile.getInputStream(zipEntry)));
                                    break;
                                }
                            }*/
                            zipFile.close();
                        } catch (Exception exception) {
                            sub.onError(exception);
                        } finally {
                            sub.onCompleted();
                        }
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static File readBoardSource(Context context, Intent intent) throws IOException {
        final String action = intent.getAction();
        final File cacheFile = File.createTempFile("board_", ".temp", CM.getBoardCacheFolder(context));
        if (action.compareTo(Intent.ACTION_VIEW) == 0) {
            String scheme = intent.getScheme();
            ContentResolver resolver = context.getContentResolver();
            final Uri uri = intent.getData();
            Timber.v("Content intent detected: %s : %s : %s : %s",
                    action,
                    intent.getDataString(),
                    intent.getType(),
                    uri.toString());
            if (intent.getData() == null) {
                return null;
            }
            if (scheme.compareTo(ContentResolver.SCHEME_CONTENT) == 0) {
                final InputStream origin = resolver.openInputStream(uri);
                CM.inputStreamToFile(origin, cacheFile);
                origin.close();
            } else if (scheme.compareTo(ContentResolver.SCHEME_FILE) == 0) {
                final InputStream origin = resolver.openInputStream(uri);
                CM.inputStreamToFile(origin, cacheFile);
                origin.close();
            } else if (scheme.compareTo("http") == 0 ||
                    scheme.compareTo("https") == 0) {
                Request.Builder builder = new Request.Builder()
                        .url(intent.getDataString());
                Response response = CM.OKHTTP().newCall(builder.build()).execute();
                if (response.isSuccessful()) {
                    CM.inputStreamToFile(response.body().byteStream(), cacheFile);
                    response.body().close();
                }
                throw new FileNotFoundException("Couldnt find http url");
            } else if (scheme.compareTo("ftp") == 0) {
                return null;
            }
        }
        return cacheFile;
    }
}
