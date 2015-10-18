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

import android.net.Uri;

/**
 * Created by Don on 18.10.2015.
 */
public final class UriBuilder {

    public static Uri getBoardUri(long id) {
        return Uri.parse("content://" + SoundBoardProvider.PROVIDER_NAME +
                SoundBoardProvider.PATH_BOARD + "/" + id);
    }

    public static Uri getSoundUri(long id) {
        return Uri.parse("content://" + SoundBoardProvider.PROVIDER_NAME +
                SoundBoardProvider.PATH_SOUND + "/" + id);
    }

    public static Uri getSoundUri() {
        return Uri.parse("content://" + SoundBoardProvider.PROVIDER_NAME +
                SoundBoardProvider.PATH_SOUND);
    }

    public static Uri getBoardUri() {
        return Uri.parse("content://" + SoundBoardProvider.PROVIDER_NAME +
                SoundBoardProvider.PATH_BOARD);
    }
}
