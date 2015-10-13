package de.xants.triitus.content;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by Don on 10.10.2015.
 */
public class CM {

    private final static long CACHE_SIZE = 50 * 1024 * 1024;
    private static OkHttpClient OK_CLIENT;
    private static Picasso PICASSO;
    private static Bus BUS = new Bus();
    private static Gson GSON;

    public static void init(Context context) {
        OK_CLIENT = new OkHttpClient();
        OK_CLIENT.setCache(new Cache(new File(context.getCacheDir(), "http"), CACHE_SIZE));
        GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        PICASSO = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(OK_CLIENT))
                .build();
    }

    public static OkHttpClient OKHTTP() {
        return OK_CLIENT;
    }

    public static Picasso PICASSO() {
        return PICASSO;
    }

    public static Bus BUS() {
        return BUS;
    }

    public static Gson GSON() {
        return GSON;
    }

    public static File getBoardCacheFolder(Context context) {
        final File file = new File(context.getCacheDir(), "boards");
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public static void readFromUri(Uri uri) {

    }


    private static void loadFromUrl(String url, File file) throws IOException {
        Response response = null;
        try {
            Request.Builder builder = new Request.Builder()
                    .get()
                    .url(url.toString());
            response = CM.OKHTTP().newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                throw new FileNotFoundException("Couldnt find file");
            }
            inputStreamToFile(response.body().byteStream(), file);
            response.body().close();
        } finally {
            if (response != null &&
                    response.body() != null)
                response.body().close();
        }
    }

    public static void inputStreamToFile(InputStream inputStream, File file) {
        try {
            Source a = Okio.source(inputStream);
            BufferedSink b = Okio.buffer(Okio.sink(file));
            b.writeAll(a);
            b.close();
            a.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
