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

package de.xants.triitus.model;

import android.net.Uri;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Don on 15.10.2015.
 */
public class MediaPath {
    private Uri mUri;

    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public static class Converter implements JsonSerializer<MediaPath>, JsonDeserializer<MediaPath> {
        @Override
        public JsonElement serialize(MediaPath src,
                                     Type typeOfSrc,
                                     JsonSerializationContext context) {
            if (src == null)
                return null;
            else if (src.mUri == null)
                return null;
            else
                return new JsonPrimitive(src.mUri.toString());
        }

        @Override
        public MediaPath deserialize(JsonElement json,
                                     Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            if (json == null || !json.isJsonPrimitive())
                return null;
            if (!json.getAsJsonPrimitive().isString())
                return null;
            try {
                final Uri uri = Uri.parse(json.getAsJsonPrimitive().getAsString());
                MediaPath mediaPath = new MediaPath();
                mediaPath.mUri = uri;
                return mediaPath;
            } catch (Exception exception) {
                return null;
            }
        }
    }
}
