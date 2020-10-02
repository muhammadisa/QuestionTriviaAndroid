package com.xoxoer.triviaquestion.ui.questions.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.xoxoer.triviaquestion.R
import kotlinx.android.synthetic.main.card_view_answer.view.*

class AnswerAdapter(
    private val questionAdapter: QuestionAdapter
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    private val answers = mutableListOf<String>()
    private val correctAnswer = ObservableField<String>()
    private val questionPosition = ObservableField<Int>()

    internal fun setAnswersAndCorrectAnswer(position: Int, correct: String, answers: List<String>) {
        this.answers.apply {
            addAll(answers)
            add(correct)
            random()
            correctAnswer.set(correct)
            questionPosition.set(position)
        }
        notifyDataSetChanged()
    }

    inner class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val constraintLayoutAnswer: ConstraintLayout = itemView.constraintLayoutAnswer
        val textViewAnswerTitle: TextView = itemView.textViewAnswerTitle
        val checkBoxAnswerSelected: CheckBox = itemView.checkBoxAnswerSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_answer, parent, false)
        return AnswerViewHolder(itemView)
    }

    override fun getItemCount(): Int = answers.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val answer = answers[position]
        with(holder) {
            textViewAnswerTitle.text = answer
            constraintLayoutAnswer.setOnClickListener {
                questionAdapter.setAnswer(questionPosition.get()!!, answer == correctAnswer.get())
            }
        }
    }

}