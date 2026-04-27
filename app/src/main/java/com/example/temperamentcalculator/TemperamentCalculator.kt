package com.example.temperamentcalculator

import android.content.Context

data class Question(
    val text: String,
    val options: List<String>
) {
    val weights = listOf(
        Pair(-1, 0), // phlegmatic
        Pair(1, 0), // choleric
        Pair(0, 1), // sanguine
        Pair(0, -1) // melancholic
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

        val q0s0 = listOf(
            context.getString(R.string.option_0_stage_0_phlegmatic),
            context.getString(R.string.option_0_stage_0_choleric),
            context.getString(R.string.option_0_stage_0_sanguine),
            context.getString(R.string.option_0_stage_0_melancholic)
        )

        val q1s0 = listOf(
            context.getString(R.string.option_1_stage_0_phlegmatic),
            context.getString(R.string.option_1_stage_0_choleric),
            context.getString(R.string.option_1_stage_0_sanguine),
            context.getString(R.string.option_1_stage_0_melancholic)
        )

        val q0s1 = listOf(
            context.getString(R.string.option_0_stage_1_phlegmatic),
            context.getString(R.string.option_0_stage_1_choleric),
            context.getString(R.string.option_0_stage_1_sanguine),
            context.getString(R.string.option_0_stage_1_melancholic)
        )

        val q1s1 = listOf(
            context.getString(R.string.option_1_stage_1_phlegmatic),
            context.getString(R.string.option_1_stage_1_choleric),
            context.getString(R.string.option_1_stage_1_sanguine),
            context.getString(R.string.option_1_stage_1_melancholic)
        )

        val q0s2 = listOf(
            context.getString(R.string.option_0_stage_2_phlegmatic),
            context.getString(R.string.option_0_stage_2_choleric),
            context.getString(R.string.option_0_stage_2_sanguine),
            context.getString(R.string.option_0_stage_2_melancholic)
        )

        val q1s2 = listOf(
            context.getString(R.string.option_1_stage_2_phlegmatic),
            context.getString(R.string.option_1_stage_2_choleric),
            context.getString(R.string.option_1_stage_2_sanguine),
            context.getString(R.string.option_1_stage_2_melancholic)
        )

        val q0s3 = listOf(
            context.getString(R.string.option_0_stage_3_phlegmatic),
            context.getString(R.string.option_0_stage_3_choleric),
            context.getString(R.string.option_0_stage_3_sanguine),
            context.getString(R.string.option_0_stage_3_melancholic)
        )

        val q1s3 = listOf(
            context.getString(R.string.option_1_stage_3_phlegmatic),
            context.getString(R.string.option_1_stage_3_choleric),
            context.getString(R.string.option_1_stage_3_sanguine),
            context.getString(R.string.option_1_stage_3_melancholic)
        )

        questions = listOf(
            Question(context.getString(R.string.question_0_stage_0), q0s0),
            Question(context.getString(R.string.question_1_stage_0), q1s0),
            Question(context.getString(R.string.question_0_stage_1), q0s1),
            Question(context.getString(R.string.question_1_stage_1), q1s1),
            Question(context.getString(R.string.question_0_stage_2), q0s2),
            Question(context.getString(R.string.question_1_stage_2), q1s2),
            Question(context.getString(R.string.question_0_stage_3), q0s3),
            Question(context.getString(R.string.question_1_stage_3), q1s3)
        )
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
