package com.example.photos_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import kotlinx.android.synthetic.main.sentiment_model.*
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




class SentimentModel : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    // private lateinit var inputEditText: EditText
    private lateinit var inputEditText: TextView
    private lateinit var executorService: ExecutorService
    private lateinit var scrollView: ScrollView
    private lateinit var predictButton: Button

    // TODO 5: Define a NLClassifier variable
    private lateinit var textClassifier: BertNLClassifier
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sentiment_model)
        Log.v(TAG, "onCreate")
        executorService = Executors.newSingleThreadExecutor()
        resultTextView = findViewById(R.id.result_text_view)
        // inputEditText = findViewById(R.id.input_text)
        inputEditText= findViewById(R.id.view_text)
        scrollView = findViewById(R.id.scroll_view)
        predictButton = findViewById(R.id.predict_button)
        view_text.setText(caption_result)

        predictButton.setOnClickListener(
            View.OnClickListener { v: View -> classify(inputEditText.getText().toString()) })

        // TODO 3: Call the method to download TFLite model
        downloadModel("MobileBERT")
    }

    /** Send input text to TextClassificationClient and get the classify messages.  */
    private fun classify(text: String) {
        executorService.execute {

            // TODO 7: Run sentiment analysis on the input text
            val results = textClassifier.classify(text)

            // Create an array to predict sentiment results
            //val names = arrayOf("Hello world","I hate you","I love you","You look awesome","You are beautiful")

            // TODO 8: Convert the result to a human-readable text
            var textToShow = "Input: $text\nOutput:\n"
            var max_value  = arrayOfNulls<Float>(3)
            var max_label = arrayOfNulls<String>(3)
            for (i in results.indices) {
                val result = results[i]
                // textToShow += String.format("    %s: %s\n", result.label, result.score)
                // Storage the result.score to max_value array
                max_value[i] = result.score
                max_label[i] = result.label
            }
            // Encontrar el score mayor

            var largest = max_value[0]
            for (num in max_value) {
                if (num != null && num > largest!!) {
                    largest = num
                }

            }
            var textToShow2 = String.format("    %s: %s\n", "Score Result", largest)

            // Encontrar el label del score mayor

            var label_predict = String()
            for (i in results.indices) {
                if (max_value[i] == largest) {
                    label_predict = max_label[i].toString()
                }
            }

            var textToShow3 = String.format("    %s: %s\n", "Label Result", label_predict)

            // Almacenar los resultados en un arreglo principal

            var main_result = mutableListOf<Any>()
            if (largest != null) {
                main_result.add(largest)
            }
            main_result.add(label_predict)

            var textToShow5= "--------------------\n"

            // Show classification result on screen
            showResult(textToShow)
            showResult(textToShow2)
            showResult(textToShow3)
            showResult(textToShow5)
            //showResult(texts.toString())

            // TODO: Jean -> Create if cycle that picks which .xml face display on screen and below the percentage
            val faceDisplay = findViewById<ImageView>(R.id.face_display)
            val percentage = largest?.times(100f)
            if (label_predict == results[0].label){
                faceDisplay.setImageResource(R.drawable.ic_sad_face)
            }
            else if (label_predict == results[1].label){
                faceDisplay.setImageResource(R.drawable.ic_neutral_face)
            }else if (label_predict == results[0].label){
                faceDisplay.setImageResource(R.drawable.ic_happy_face)
            }
        }
    }

    /** Show classification result on the screen.  */
    private fun showResult(textToShow: String) {
        // Run on UI thread as we'll updating our app UI
        runOnUiThread {

            // Append the result to the UI.
            resultTextView.append(textToShow)

            // Clear the input text.
            // inputEditText.text.clear()

            // Scroll to the bottom to show latest entry's classification result.
            scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
        }
    }
    // TODO 2: Implement a method to download TFLite model from Firebase
    /** Download model from Firebase ML.  */
    @Synchronized
    private fun downloadModel(modelName: String) {
        val remoteModel = FirebaseCustomRemoteModel.Builder(modelName)
            .build()
        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        val firebaseModelManager = FirebaseModelManager.getInstance()
        firebaseModelManager
            .download(remoteModel, conditions)
            .continueWithTask { task: Task<Void> ->
                firebaseModelManager.getLatestModelFile(
                    remoteModel
                )
            }
            .continueWith(executorService, Continuation<File, Void> { task: Task<File> ->
                // Initialize a text classifier instance with the model
                val modelFile = task.result

                // TODO 6: Initialize a TextClassifier with the downloaded model
                textClassifier = BertNLClassifier.createFromFile(modelFile)

                // Enable predict button
                predictButton.isEnabled = true
                null
            })
            .addOnFailureListener { e: Exception? ->
                Log.e(TAG, "Failed to download and initialize the model. ", e)
                Toast.makeText(
                    this@SentimentModel,
                    "Model download failed, please check your connection.",
                    Toast.LENGTH_LONG
                )
                    .show()
                predictButton.isEnabled = false
            }
    }
}