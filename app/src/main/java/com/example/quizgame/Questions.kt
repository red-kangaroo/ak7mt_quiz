package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

class Questions : AppCompatActivity() {
    companion object {
        var allJoinedFeed: ArrayList<JoinedFeed> = ArrayList()
        var answerSelected: ArrayList<String> = ArrayList()
        var questionNumber: Int = 0
        var answerOK: Int = 0
        var answerNOK: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        supportActionBar?.hide()

        val endpoint: String = "https://opentdb.com/api.php?amount=10&difficulty=easy&type=multiple"
        val questions: ArrayList<String> = ArrayList()
        val answers: ArrayList<ArrayList<String>> = ArrayList()
        val allOKAnswers: ArrayList<String> = ArrayList()

        val doneOverlay: ConstraintLayout = findViewById(R.id.quizDoneOverlay)
        doneOverlay.visibility = View.GONE

        val httpAsync = endpoint.httpGet()
            .responseString{request, response, result ->
                when(result){
                    is com.github.kittinunf.result.Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val rawData: String = result.get()
                        val jsonBuilder = GsonBuilder().create()

                        val data: ResultList = jsonBuilder.fromJson(rawData, ResultList::class.java)
                        for(value in data.results){
                            questions.add(value.question)

                            val allAnswers = value.incorrect_answers
                            allAnswers.add((0..3).random(), value.correct_answer)
                            answers.add(allAnswers)

                            allOKAnswers.add(value.correct_answer)
                        }
                    }
                }
            }
        httpAsync.join()


    }
}
