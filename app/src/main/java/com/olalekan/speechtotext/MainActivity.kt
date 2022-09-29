package com.olalekan.speechtotext

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Button;
import java.util.*
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.res.ResourcesCompat
import com.olalekan.speechtotext.Utilities.REQUEST_CODE_FOR_SPEECH_INPUT

class MainActivity : AppCompatActivity() {
//    private val REQUEST_CODE_FOR_SPEECH_INPUT = 1;
    private val MAX_NUMBER_OF_TIMES = 10;
    private var numberOfTimesRepeated = 0;
    private var randomNumber1 = 0
    private var randomNumber2 = 0
    private val RANGE:IntRange = 0..10

    private lateinit var button:Button;
    private lateinit var randomNumberText : TextView;
    lateinit var textToSpeech:TextToSpeech
    lateinit var replyTextToSpeech:TextToSpeech

    var textToSpeechReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button = findViewById(R.id.start_button)
        randomNumberText = findViewById(R.id.random_numbers_text)


    }

    override fun onStart() {
        super.onStart()

        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS){
                Log.d("TextToSpeech", "Success, textToSpeech initialisation")
                button.setBackgroundColor(Color.BLUE)
                textToSpeechReady = true;
            }else Log.d("TextToSpeech", "Error, textToSpeech initialisation")
        })

        textToSpeech.setOnUtteranceProgressListener(MyUtteranceProgressListener(this))

        replyTextToSpeech = TextToSpeech(this) { }
    }

    override fun onStop() {
        super.onStop()
        textToSpeech.stop();
        replyTextToSpeech.stop()
        button.setBackgroundColor(Color.BLACK)
        textToSpeechReady = false;
    }


    fun startSpeaking(view: View) {
        if(!textToSpeechReady) return;
        setAndShowNewRandomNumbers();

        Utilities.textToSpeech("Multiply $randomNumber1 by $randomNumber2", textToSpeech)

        if (button.text == getString(R.string.restart) || button.text == "Start")
            button.text = getString(R.string.answer)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_FOR_SPEECH_INPUT) {
            if(resultCode == RESULT_OK && data!=null){
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val ouput = result?.get(0) ?: "null";


                var whatToSay : String = "wrong"

                if(ouput.equals((randomNumber1*randomNumber2).toString())) whatToSay = "Correct"

                Utilities.textToSpeech(
                    whatToSay,
                    replyTextToSpeech
                )

            }
        }


        if(numberOfTimesRepeated<MAX_NUMBER_OF_TIMES){
            ++numberOfTimesRepeated
            findViewById<TextView>(R.id.number_of_times_repeated).text = "$numberOfTimesRepeated";
            startSpeaking(View(this))
        } else {
            randomNumberText.text = getString(R.string.done)
            Utilities.textToSpeech("Done!", replyTextToSpeech)
            button.text = getString(R.string.restart)
            numberOfTimesRepeated =0;
        }
    }

    private fun setAndShowNewRandomNumbers(){
        randomNumber1 = (RANGE).random();
        randomNumber2 = (RANGE).random();
        val question = "$randomNumber1  *  $randomNumber2"
        randomNumberText.setText(question)
    }

    fun generateRandomNumbers() = arrayOf(
        (0 .. 100).random(),
        (0 .. 100).random()
    );


}