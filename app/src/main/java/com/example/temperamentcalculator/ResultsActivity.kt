package com.example.temperamentcalculator

import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.charts.ScatterChart

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.temperamentcalculator.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val x = intent.getIntExtra("scoreX", 0)
        val y = intent.getIntExtra("scoreY", 0)

        setupTemperamentChart(x, y)

        // --- Determine temperament strengths (clean version) ---
        val temperamentScores = mapOf(
            getString(R.string.choleric) to maxOf(0, x),
            getString(R.string.phlegmatic) to maxOf(0, -x),
            getString(R.string.sanguine) to maxOf(0, y),
            getString(R.string.melancholic) to maxOf(0, -y)
        )

        val sorted = temperamentScores.entries.sortedByDescending { it.value }

        val topTemperament = sorted[0].key
        val secondTemperament = sorted[1].key

        binding.finalTemperament.text =
            getString(R.string.final_temperament, topTemperament, secondTemperament)

        // Raw coordinate display
        binding.coordinateScore.text = getString(R.string.score_coordinate, x, y)

        binding.repeatButton.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        binding.homeButton1.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupTemperamentChart(x: Int, y: Int) {
        val maxGraphScore = 40f

        val chart = binding.temperamentChart

        // --- Clean base style ---
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)

        // --- Prevent clipping ---
        chart.setExtraOffsets(maxGraphScore, maxGraphScore, maxGraphScore, maxGraphScore)

        // --- Remove number labels for perfect centering ---
        chart.xAxis.setDrawLabels(false)
        chart.axisLeft.setDrawLabels(false)
        chart.axisRight.setDrawLabels(false)

        // --- Grid ON, borders OFF ---
        chart.xAxis.setDrawGridLines(true)
        chart.axisLeft.setDrawGridLines(true)
        chart.axisRight.isEnabled = false

        chart.xAxis.setDrawAxisLine(false)
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisRight.setDrawAxisLine(false)

        // --- Axis bounds (updated for 16 questions) ---
        chart.xAxis.axisMinimum = -maxGraphScore
        chart.xAxis.axisMaximum = maxGraphScore
        chart.axisLeft.axisMinimum = -maxGraphScore
        chart.axisLeft.axisMaximum = maxGraphScore

        // Soft green (not neon)
        val softGreen = Color.rgb(80, 200, 120)

        // --- Theme adaptation ---
        val isDark = (resources.configuration.uiMode
                and android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES

        val fg = if (isDark) Color.WHITE else Color.BLACK

        // --- Draw cross using faint limit lines ---
        val centerX = com.github.mikephil.charting.components.LimitLine(0f).apply {
            lineWidth = 4f
            lineColor = Color.argb(50, Color.red(fg), Color.green(fg), Color.blue(fg))
        }

        val centerY = com.github.mikephil.charting.components.LimitLine(0f).apply {
            lineWidth = 4f
            lineColor = Color.argb(50, Color.red(fg), Color.green(fg), Color.blue(fg))
        }

        chart.xAxis.addLimitLine(centerX)
        chart.axisLeft.addLimitLine(centerY)

        chart.setBackgroundColor(Color.TRANSPARENT)

        // --- Plot temperament point ---
        val entries = listOf(
            com.github.mikephil.charting.data.Entry(x.toFloat(), y.toFloat())
        )

        val dataSet = ScatterDataSet(entries, "Temperament").apply {
            color = softGreen
            setScatterShape(ScatterChart.ScatterShape.CIRCLE)
            scatterShapeSize = 40f
            setDrawHighlightIndicators(false)
            isHighlightEnabled = false
        }

        chart.data = com.github.mikephil.charting.data.ScatterData(dataSet)

        chart.invalidate()
    }
}
