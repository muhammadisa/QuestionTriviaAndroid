package com.xoxoer.triviaquestion.ui.questions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.models.Questions
import com.xoxoer.triviaquestion.models.Result
import com.xoxoer.triviaquestion.ui.questions.adapter.QuestionAdapter
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionRecyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter

    private fun initUserAdapter(result: List<Result>) {
        questionAdapter = QuestionAdapter(this, result)
        questionRecyclerView = findViewById(R.id.recyclerViewQuestion)
        questionRecyclerView.apply {
            setHasFixedSize(true)
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(this@QuestionActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        initUserAdapter(
            intent.getParcelableExtra<Questions>("Questions")?.results!!
        )

        buttonFinishQuiz.setOnClickListener {
            val answers = questionAdapter.getAnswers()
            val correctAnswer = answers.filter { it }
            Toast.makeText(
                this@QuestionActivity,
                "Right ${correctAnswer.size} from ${answers.size}",
                Toast.LENGTH_SHORT
            ).show()
            questionAdapter.clearAnswers()
        }
    }
}