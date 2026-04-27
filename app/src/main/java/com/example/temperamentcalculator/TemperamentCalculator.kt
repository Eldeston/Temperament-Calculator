package com.example.temperamentcalculator

import android.content.Context

data class Question(
    val text: String,
    val options: List<String>
) {
    val weights = listOf(
        Pair(-1, 0),  // phlegmatic
        Pair(1, 0),   // choleric
        Pair(0, 1),   // sanguine
        Pair(0, -1)   // melancholic
    )
}

class TemperamentCalculator(context: Context) {

    private val questions: List<Question>
    private var index = 0

    var scoreX = 0
        private set
    var scoreY = 0
        private set

    init {
        println("TemperamentCalculator LOADED")

        fun opts(stage: Int, q: Int) = listOf(
            context.getString(context.resources.getIdentifier("option_${q}_stage_${stage}_phlegmatic", "string", context.packageName)),
            context.getString(context.resources.getIdentifier("option_${q}_stage_${stage}_choleric", "string", context.packageName)),
            context.getString(context.resources.getIdentifier("option_${q}_stage_${stage}_sanguine", "string", context.packageName)),
            context.getString(context.resources.getIdentifier("option_${q}_stage_${stage}_melancholic", "string", context.packageName))
        )

        fun qText(stage: Int, q: Int) =
            context.getString(context.resources.getIdentifier("question_${q}_stage_${stage}", "string", context.packageName))

        // Build all 16 questions dynamically
        val list = mutableListOf<Question>()

        for (stage in 0..3) {
            for (q in 0..3) {
                list += Question(
                    text = qText(stage, q),
                    options = opts(stage, q)
                )
            }
        }

        questions = list
    }

    fun getCurrentQuestion(): Question = questions[index]

    fun submitAnswer(optionIndex: Int) {
        val (x, y) = questions[index].weights[optionIndex]
        scoreX += x
        scoreY += y
        index++
    }

    fun isFinished(): Boolean = index >= questions.size

    fun getCurrentIndex(): Int = index
}
