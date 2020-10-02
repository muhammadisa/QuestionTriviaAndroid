package com.xoxoer.triviaquestion.ui.questions.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.models.Result
import com.xoxoer.triviaquestion.util.common.conditionalVisibility
import kotlinx.android.synthetic.main.card_view_questions.view.*

class QuestionAdapter(
    private val activity: Activity,
    private val questions: MutableList<Result>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private lateinit var answerAdapter: AnswerAdapter
    private val answered: MutableList<Boolean?> = questions.map { null }.toMutableList()

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
    }

    fun setAnswer(position: Int, value: Boolean) {
        answered[position] = value
        activity.findViewById<Button>(R.id.buttonFinishQuiz)
            .conditionalVisibility(answered.size == questions.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_questions, parent, false)
        answerAdapter = AnswerAdapter(this)
        return QuestionViewHolder(itemView)
    }

    override fun getItemCount(): Int = questions.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]

        when (question.type) {
            "multiple" -> {
                setupAnswerAdapter(holder)
                answerAdapter.setAnswersAndCorrectAnswer(
                    position,
                    question.correctAnswer,
                    question.incorrectAnswers
                )
            }
            else -> {
            }
        }

        with(holder) {
            textViewQuestion.text = question.question
            linearLayoutMultipleType.conditionalVisibility(question.type == "multiple")
            linearLayoutBooleanType.conditionalVisibility(question.type == "boolean")
        }
    }

}