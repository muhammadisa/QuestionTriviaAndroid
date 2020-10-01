package com.xoxoer.triviaquestion.ui.questions.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.models.Result
import com.xoxoer.triviaquestion.util.common.conditionalVisibility
import com.xoxoer.triviaquestion.util.common.setupSpinner
import kotlinx.android.synthetic.main.card_view_questions.view.*

class QuestionAdapter(
    private val activity: Activity,
    private val questions: List<Result>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private val answers: MutableList<Boolean> = mutableListOf()

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewQuestion: TextView = itemView.textViewQuestion

        val linearLayoutBooleanType: LinearLayout = itemView.linearLayoutBooleanType
        val linearLayoutMultipleType: LinearLayout = itemView.linearLayoutMultipleType

        val spinnerAnswer: Spinner = itemView.spinnerAnswer
    }

    internal fun getAnswers(): List<Boolean> {
        return answers
    }

    internal fun clearAnswers() {
        answers.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_questions, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun getItemCount(): Int = questions.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        val randomizedAnswer = mutableListOf(question.correctAnswer)

        question.incorrectAnswers.forEach { randomizedAnswer.add(it) }
        randomizedAnswer.shuffle()

        with(holder) {
            textViewQuestion.text = question.question

            linearLayoutMultipleType.conditionalVisibility(question.type == "multiple")
            linearLayoutBooleanType.conditionalVisibility(question.type == "boolean")

            when (question.type) {
                "multiple" -> {
                    spinnerAnswer.setupSpinner(
                        holder.itemView.context,
                        randomizedAnswer.toTypedArray(),
                        nothingSelected = {},
                        itemSelected = { positionSpinner -> }
                    )
                }
            }
        }
    }

}