package com.example.temperamentcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.temperamentcalculator.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var calculator: TemperamentCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_second)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calculator = TemperamentCalculator(this)
        showQuestion()

        binding.optionButton0.setOnClickListener { handleAnswer(0) }
        binding.optionButton1.setOnClickListener { handleAnswer(1) }
        binding.optionButton2.setOnClickListener { handleAnswer(2) }
        binding.optionButton3.setOnClickListener { handleAnswer(3) }
    }

    private fun showQuestion() {
        val question = calculator.getCurrentQuestion()

        binding.questionLabel.text = question.text
        binding.optionButton0.text = question.options[0]
        binding.optionButton1.text = question.options[1]
        binding.optionButton2.text = question.options[2]
        binding.optionButton3.text = question.options[3]

        val questionNum = calculator.getCurrentIndex()
        val stageNum = questionNum / 4

        binding.stageLabel.text = getString(R.string.stage_label, stageNum + 1, questionNum + 1)
    }

    private fun handleAnswer(optionIndex: Int) {
        calculator.submitAnswer(optionIndex)

        if (calculator.isFinished()) {
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("scoreX", calculator.scoreX)
            intent.putExtra("scoreY", calculator.scoreY)

            startActivity(intent)
            return
        }

        showQuestion()
    }
}