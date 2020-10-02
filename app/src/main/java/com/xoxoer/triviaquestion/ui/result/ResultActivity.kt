package com.xoxoer.triviaquestion.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.ui.mainmenu.MainMenuActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    private fun calculateScore(answers: ArrayList<String>) {
        val rightAnswers = answers.filter { it == "true" }
        val wrongAnswers = answers.filter { it == "false" }
        val total = (rightAnswers.size * 100) / answers.size
        textViewScore.text = total.toString()
        textViewDescription.text =
            "Right/Wrong ${rightAnswers.size}/${wrongAnswers.size} from ${answers.size}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        intent.getStringArrayListExtra("Answers").apply {
            calculateScore(this!!)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ResultActivity, MainMenuActivity::class.java))
    }
}