package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.Build
//import android.text.Html
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.google.gson.GsonBuilder

class Questions : AppCompatActivity() {
    companion object {
        var allJoinedFeed: JoinedFeed? = null
        var questionNumber: Int = 0
        var answerOK: Int = 0
        var answerNOK: Int = 0
        // Settings:
        var questionTotal: Int = 10
        var questionDiff: String = "easy"
        var questionType: String = "multiple"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        supportActionBar?.hide()

        val endpoint = "https://opentdb.com/api.php?amount=${questionTotal}&difficulty=${questionDiff}&type=${questionType}"
        val questions: ArrayList<String> = ArrayList()
        val answers: ArrayList<ArrayList<String>> = ArrayList()
        val allOKAnswers: ArrayList<String> = ArrayList()

        // Reset questions:
        questionNumber = 0
        answerOK = 0
        answerNOK = 0

        val doneOverlay: ConstraintLayout = findViewById(R.id.quizDoneOverlay)
        doneOverlay.visibility = View.GONE
        val timeInfo: TextView = findViewById(R.id.timeLeft)
        timeInfo.visibility = View.GONE

        val nxtButton = findViewById<ImageButton>(R.id.nextButton)
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, Stats::class.java)
            startActivity(intent)
            finish()
        }

        val httpAsync = endpoint.httpGet()
            .responseString{request, response, result ->
                when(result){
                    is com.github.kittinunf.result.Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                        Toast.makeText(this,"Failed to load questions.", Toast.LENGTH_LONG).show()
                    }
                    is com.github.kittinunf.result.Result.Success -> {
                        val rawData: String = result.get()
                        val jsonBuilder = GsonBuilder().create()
//                        val rawData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            Html.fromHtml(rawHtml, Html.FROM_HTML_MODE_LEGACY).toString()
//                        } else {
//                            Html.fromHtml(rawHtml).toString()
//                        }

                        val data: ResultList = jsonBuilder.fromJson(rawData, ResultList::class.java)
                        for(value in data.results){
                            questions.add(resetHtml(value.question))

                            val allAnswers = value.incorrect_answers
                            allAnswers.add((0..3).random(), resetHtml(value.correct_answer))
                            answers.add(allAnswers)

                            allOKAnswers.add(resetHtml(value.correct_answer))
                        }
                    }
                }
            }
        httpAsync.join()

        allJoinedFeed = JoinedFeed(questions=questions, answers=answers, answerOK=allOKAnswers)
        startQuiz()

        val shownAnswers = findViewById<ListView>(R.id.answerBox)
        val currentAnswers: ArrayList<String> = allJoinedFeed!!.answers[questionNumber - 1]
        val correctAnswer = allJoinedFeed!!.answerOK[questionNumber - 1]

        shownAnswers.setOnItemClickListener { parent, view, position, id ->
            val clicked = id.toInt()
            val selectedAnswer = currentAnswers[clicked]

            if(selectedAnswer == correctAnswer) {
                answerOK++
            } else {
                answerNOK++
            }

            if(questionNumber == questionTotal){
                stopQuiz()
            } else {
                startQuiz()
            }
        }
        nxtButton.setOnClickListener {
            answerNOK++

            if(questionNumber == questionTotal){
                stopQuiz()
            } else {
                startQuiz()
            }
        }
    }

    private fun startQuiz(){
        val totalQuestionNumber = findViewById<TextView>(R.id.questionEnum)
        val shownQuestionNumber = findViewById<TextView>(R.id.questionNo)
        val activeQuestion = findViewById<TextView>(R.id.questionText)

        val currentQuestion = allJoinedFeed!!.questions[questionNumber]
        val currentAnswers: ArrayList<String> = allJoinedFeed!!.answers[questionNumber]

        questionNumber++
        totalQuestionNumber.text = "${questionNumber.toString()}/${questionTotal.toString()}"
        shownQuestionNumber.text = "${questionNumber.toString()}."

        activeQuestion.text = currentQuestion
        setAnswers(currentAnswers)
    }

    private fun stopQuiz(){
        val doneOverlay = findViewById<ConstraintLayout>(R.id.quizDoneOverlay)
        val donePupUp = findViewById<ListView>(R.id.quizDonePopUp)


        doneOverlay.visibility = View.VISIBLE
        val doneInfo = DoneFeed(
            answersOK= answerOK,
            answersNOK = answerNOK,
            questionNumber = questionNumber
        )
        donePupUp.adapter = DoneAdapter(this, doneInfo)
    }

    private fun setAnswers(answers: ArrayList<String>){
        val shownAnswers = findViewById<ListView>(R.id.answerBox)
        for(a in answers){
            shownAnswers.adapter = AnswerAdapter(this, answers)
        }
    }

    private fun resetHtml(str: String): String {
        return str
            .replace("&#039;", "'")
            .replace("&quot;", "\"")
            .replace("&rsquo;", "'")
            .replace("&shy;", "-")
    }
}
