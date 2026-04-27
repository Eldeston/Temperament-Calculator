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
        val q = calculator.getCurrentQuestion()

        binding.questionLabel.text = q.text
        binding.optionButton0.text = q.options[0]
        binding.optionButton1.text = q.options[1]
        binding.optionButton2.text = q.options[2]
        binding.optionButton3.text = q.options[3]

        val stage = calculatorIndexToStage()

        val stageStringId = when (stage) {
            0 -> R.string.stage_0
            1 -> R.string.stage_1
            2 -> R.string.stage_2
            3 -> R.string.stage_3
            else -> R.string.stage_0
        }

        binding.stageLabel.text = getString(stageStringId)
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

    private fun calculatorIndexToStage(): Int {
        return calculator.getCurrentIndex() / 4
    }
}