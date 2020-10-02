package com.xoxoer.triviaquestion.ui.questions.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.models.Result
import com.xoxoer.triviaquestion.util.common.conditionalVisibility
import com.xoxoer.triviaquestion.util.common.gone
import kotlinx.android.synthetic.main.card_view_questions.view.*

class QuestionAdapter(
    private val activity: Activity,
    private val questions: MutableList<Result>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private lateinit var answerAdapter: AnswerAdapter
    private val correct = MutableLiveData<Boolean>()
    private val answered: MutableList<Boolean?> = mutableListOf()

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewQuestion: TextView = itemView.textViewQuestion
        val linearLayoutBooleanType: LinearLayout = itemView.linearLayoutBooleanType
        val linearLayoutMultipleType: LinearLayout = itemView.linearLayoutMultipleType
        val recyclerViewAnswer: RecyclerView = itemView.recyclerViewAnswer
    }

    private fun setupAnswerAdapter(holder: QuestionViewHolder) {
        with(holder) {
            recyclerViewAnswer.apply {
                setHasFixedSize(true)
                adapter = answerAdapter
                layoutManager = LinearLayoutManager(itemView.context)
            }
        }
        questions.forEach { _ -> answered.add(null) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_questions, parent, false)
        answerAdapter = AnswerAdapter(activity, correct)
        return QuestionViewHolder(itemView)
    }

    override fun getItemCount(): Int = questions.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        with(holder) {
            textViewQuestion.text = question.question

            linearLayoutMultipleType.conditionalVisibility(question.type == "multiple")
            linearLayoutBooleanType.conditionalVisibility(question.type == "boolean")

            when (question.type) {
                "multiple" -> {
                    setupAnswerAdapter(this)
                    answerAdapter.setAnswersAndCorrectAnswer(
                        position,
                        question.correctAnswer,
                        question.incorrectAnswers
                    )
                    answerAdapter.onAnswer { isCorrect ->
                        isCorrect.observeForever {
                            answered.add(position, it)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

}