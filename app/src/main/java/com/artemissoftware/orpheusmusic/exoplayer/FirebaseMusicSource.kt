package com.artemissoftware.orpheusmusic.exoplayer

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import com.artemissoftware.orpheusmusic.data.remote.MusicDatabase
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseMusicSource @Inject constructor(private val musicDatabase: MusicDatabase, private val dataSourceFactory: DefaultDataSourceFactory){

    private var songs = emptyList<MediaMetadataCompat>()



    suspend fun fetchMediaData() = withContext(Dispatchers.IO){
        state = State.STATE_INITIALIZING

        val allSongs = musicDatabase.getAllSongs()

        songs = allSongs.map {
            MediaMetadataCompat.Builder()
                .putString(METADATA_KEY_ARTIST, it.subtitle)
                .putString(METADATA_KEY_MEDIA_ID, it.mediaId)
                .putString(METADATA_KEY_TITLE, it.title)
                .putString(METADATA_KEY_MEDIA_URI, it.songUrl)
                .putString(METADATA_KEY_ALBUM_ART_URI, it.imageUrl)
                .putString(METADATA_KEY_DISPLAY_SUBTITLE, it.subtitle)
                .putString(METADATA_KEY_DISPLAY_DESCRIPTION, it.subtitle)
                .putString(METADATA_KEY_DISPLAY_TITLE, it.title)
                .putString(METADATA_KEY_DISPLAY_ICON_URI, it.imageUrl)
                .build()
        }

        state = State.STATE_INITIALIZED
    }



    fun asMediaSource(dataSourceFactory: DefaultDataSourceFactory): ConcatenatingMediaSource{

        val concatenatingMediaSource = ConcatenatingMediaSource()

        songs.forEach {

        }

    }



    private val onReadyListeners = mutableListOf<(Boolean) -> Unit>()

    private var state: State = State.STATE_CREATED
        set(value){
            if(value == State.STATE_INITIALIZED || value == State.STATE_ERROR){

                synchronized(onReadyListeners){
                    field = value
                    onReadyListeners.forEach { listener->
                        listener(state == State.STATE_INITIALIZED)
                    }
                }
            }
            else{
                field = value
            }
        }


    fun whenReady(action: (Boolean) -> Unit): Boolean{

        if(state == State.STATE_CREATED || state == State.STATE_INITIALIZING){

            onReadyListeners += action
            return false
        }
        else{
            action(state == State.STATE_INITIALIZED)
            return false
        }

    }
}

enum class State{

    STATE_CREATED, STATE_INITIALIZING, STATE_INITIALIZED, STATE_ERROR

}