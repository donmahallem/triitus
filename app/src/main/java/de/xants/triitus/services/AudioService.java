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

package de.xants.triitus.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by Don on 17.10.2015.
 */
public class AudioService extends Service
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {
    public static final String ACTION_PLAY = "de.xants.triitus.action.play";
    public static final String ACTION_PAUSE = "de.xants.triitus.action.pause";
    public static final String ACTION_STOP = "de.xants.triitus.action.stop";
    private MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand(%s,%s,%s)", intent.getAction(), flags, startId);
        //Requesting AudioManager
        this.mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (intent.getAction().equals(ACTION_PLAY)) {
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                this.mMediaPlayer.setDataSource(getApplicationContext(), intent.getData());
            } catch (IOException e) {
                Timber.e(e, "Couldnt set DataSource");
                this.mMediaPlayer.release();
                return START_NOT_STICKY;
            }
            this.mMediaPlayer.setOnPreparedListener(this);
            this.mMediaPlayer.setOnErrorListener(this);
            this.mMediaPlayer.setOnCompletionListener(this);
            this.mMediaPlayer.prepareAsync();
        } else if (intent.getAction().equals(ACTION_STOP)) {
            stopSelf();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy()");
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
        }
        this.mMediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Timber.d("onPrepared(%s)", mp.getAudioSessionId());
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Timber.d("onCompletion(%s)", mp.getAudioSessionId());
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Timber.d("onError(%s,%s,%s)", mp.getAudioSessionId(), what, extra);
        return false;
    }

    //See http://developer.android.com/guide/topics/media/mediaplayer.html#audiofocus
    @Override
    public void onAudioFocusChange(int focusChange) {
        Timber.d("onAudioFocusChange(%s)", focusChange);
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (this.mMediaPlayer == null) initMediaPlayer();
                else if (!this.mMediaPlayer.isPlaying())
                    this.mMediaPlayer.start();
                this.mMediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (this.mMediaPlayer.isPlaying())
                    this.mMediaPlayer.stop();
                this.mMediaPlayer.release();
                this.mMediaPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (this.mMediaPlayer.isPlaying())
                    this.mMediaPlayer.pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (this.mMediaPlayer.isPlaying())
                    this.mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }

    }

    private void initMediaPlayer() {

    }
}