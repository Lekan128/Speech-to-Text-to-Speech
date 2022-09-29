package com.olalekan.speechtotext;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Utilities {
    public static int REQUEST_CODE_FOR_SPEECH_INPUT = 1;

    public static void  activateSpeechToText(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the answer?");
        //        val resultLauncher : ActivityResultLauncher<>()
        //        resultLauncher.lauch()
        startActivityForResult(activity, intent, REQUEST_CODE_FOR_SPEECH_INPUT, null);
    }

    public static void textToSpeech(String whatToSay, TextToSpeech textToSpeech){
        speak(whatToSay, textToSpeech);

        textToSpeech.setLanguage(Locale.UK);
    }

    public static void speak(String whatToSay, TextToSpeech textToSpeech) {
        int result = textToSpeech.speak(whatToSay, TextToSpeech.QUEUE_ADD, null, "unique identifyer");
//        if(textToSpeech.i)
        if (result == TextToSpeech.SUCCESS){
//            activateSpeechToText();

            //TODO: Fix this
//            val utteranceProgressListener = UtteranceProgressListener()
//            textToSpeech.setOnUtteranceProgressListener(MyUtteranceProgressListener())

            Log.d("TextToSpeech", "Success, textToSpeech speak");
        }else Log.d("TextToSpeech", "Error, textToSpeech speak");
    }

}
