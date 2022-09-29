package com.olalekan.speechtotext

import android.app.Activity
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.olalekan.speechtotext.Utilities.activateSpeechToText

class MyUtteranceProgressListener (val activity: Activity) : UtteranceProgressListener(){

    override fun onStart(p0: String?) {
        Log.d("Utterance", "onStart: ")
//            TODO("Not yet implemented")
    }

    override fun onDone(p0: String?) {
//            activateSpeechToText()
        Log.d("Utterance", "onDone: ${p0}")
        activateSpeechToText(activity)
//            TODO("Not yet implemented")
    }

    override fun onError(p0: String?) {
        Log.d("Utterance", "onError: ")
//            TODO("Not yet implemented")
    }

}