package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

class Questions : AppCompatActivity() {
    companion object {
        var allJoinedFeed: JoinedFeed? = null
        var answerSelected: ArrayList<String> = ArrayList()
        var questionNumber: Int = 0
        var questionTotal: Int = 10
        var answerOK: Int = 0
        var answerNOK: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        supportActionBar?.hide()

        val endpoint: String = "https://opentdb.com/api.php?amount=${questionTotal}&difficulty=easy&type=multiple"
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

        allJoinedFeed = JoinedFeed(questions=questions, answers=answers, answerOK=allOKAnswers)
        startQuiz() // TODO
    }

    private fun startQuiz(){
        val nxtButton = findViewById<ImageButton>(R.id.nextButton)
        val totalQuestionNumber = findViewById<TextView>(R.id.questionEnum)
        val shownQuestionNumber = findViewById<TextView>(R.id.questionNo)
        val activeQuestion = findViewById<TextView>(R.id.questionText)
        val shownAnswers = findViewById<ListView>(R.id.answerBox)

        val doneOverlay = findViewById<ConstraintLayout>(R.id.quizDoneOverlay)
        val donePupUp = findViewById<ListView>(R.id.quizDonePopUp)
        doneOverlay.visibility = View.GONE

        val currentQuestion = allJoinedFeed!!.questions[questionNumber]
        val currentAnswers: ArrayList<String> = allJoinedFeed!!.answers[questionNumber]
        val correctAnswer = allJoinedFeed!!.answerOK[questionNumber]

        questionNumber++
        totalQuestionNumber.text = "${questionNumber.toString()}/${questionTotal.toString()}"
        shownQuestionNumber.text = "${questionNumber.toString()}."

        activeQuestion.text = currentQuestion
        setAnswers(currentAnswers)

        shownAnswers.setOnItemClickListener { parent, view, position, id ->
            val clicked = id.toInt()
//            println(clicked)

            val selectedAnswer = currentAnswers[clicked]

            if(selectedAnswer == correctAnswer) {
                answerOK++
            } else {
                answerNOK++
            }

            if(questionNumber == questionTotal){
                doneOverlay.visibility = View.VISIBLE

                val doneInfo = DoneFeed(
                    answersOK= answerOK,
                    answersNOK = answerNOK
                )
                donePupUp.adapter = DoneAdapter(this, doneInfo)
            } else {

            }
        }
    }

    private fun setAnswers(answers: ArrayList<String>){
        val shownAnswers = findViewById<ListView>(R.id.answerBox)
        for(a in answers){
            shownAnswers.adapter = AnswerAdapter(this, answers)
        }
    }
}
