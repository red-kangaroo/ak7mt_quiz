package com.example.quizgame

class StatFeed(
    val name: String,
    val image: Int
)

class ResultList(
    val results: ArrayList<ResultFeed>,
    val response_code: Int
)

class QuizResult(
    val result: String
)

class ResultFeed(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: ArrayList<String>
)

class JoinedFeed(
    val questions: ArrayList<String>,
    val answers: ArrayList<ArrayList<String>>,
    val answerOK: ArrayList<String>
)
