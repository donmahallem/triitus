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

package de.xants.triitus.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Don on 14.10.2015.
 */
public abstract class CustomizedTypeAdapterFactory<C>
        implements TypeAdapterFactory {
    private final Class<C> customizedClass;

    public CustomizedTypeAdapterFactory(Class<C> customizedClass) {
        this.customizedClass = customizedClass;
    }

    @SuppressWarnings("unchecked") // we use a runtime check to guarantee that 'C' and 'T' are equal
    public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        return type.getRawType() == customizedClass
                ? (TypeAdapter<T>) customizeMyClassAdapter(gson, (TypeToken<C>) type)
                : null;
    }

    private TypeAdapter<C> customizeMyClassAdapter(Gson gson, TypeToken<C> type) {
        final TypeAdapter<C> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
        return new TypeAdapter<C>() {
            @Override
            public void write(JsonWriter out, C value) throws IOException {
                JsonElement tree = delegate.toJsonTree(value);
                beforeWrite(value, tree);
                elementAdapter.write(out, tree);
            }

            @Override
            public C read(JsonReader in) throws IOException {
                JsonElement tree = elementAdapter.read(in);
                afterRead(tree);
                return delegate.fromJsonTree(tree);
            }
        };
    }

    /**
     * Override this to muck with {@code toSerialize} before it is written to
     * the outgoing JSON stream.
     */
    protected void beforeWrite(C source, JsonElement toSerialize) {
    }

    /**
     * Override this to muck with {@code deserialized} before it parsed into
     * the application type.
     */
    protected void afterRead(JsonElement deserialized) {
    }
}
