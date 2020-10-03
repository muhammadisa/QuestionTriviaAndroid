package com.xoxoer.triviaquestion.ui.mainmenu

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xoxoer.triviaquestion.R
import com.xoxoer.triviaquestion.models.Category
import com.xoxoer.triviaquestion.ui.questions.QuestionActivity
import com.xoxoer.triviaquestion.util.common.createLoading
import com.xoxoer.triviaquestion.util.common.onTextChange
import com.xoxoer.triviaquestion.util.common.setupSpinner
import com.xoxoer.triviaquestion.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.util.*
import javax.inject.Inject

class MainMenuActivity : DaggerAppCompatActivity() {

    private lateinit var questionTriviaViewModel: QuestionTriviaViewModel
    private lateinit var loadingDialog: Dialog

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val categories = arrayOf(
        Category("27:Animals"),
        Category("18:Computer"),
        Category("9:General"),
        Category("21:Sports"),
        Category("23:History")
    )

    private val difficulties = arrayOf(
        "Easy",
        "Medium",
        "Hard"
    )

    private val types = arrayOf(
        "boolean",
        "multiple"
    )

    private fun initUI() {
        loadingDialog = createLoading()

        editTextAmountOfQuiz.onTextChange { amount ->
            try {
                questionTriviaViewModel.amount.set(amount.toInt())
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }

        spinnerCategory.setupSpinner(
            this@MainMenuActivity,
            categories,
            nothingSelected = {},
            itemSelected = { position ->
                questionTriviaViewModel.category
                    .set(categories[position].categoryName.split(":")[0].toInt())
            }
        )

        spinnerDifficulty.setupSpinner(
            this@MainMenuActivity,
            difficulties,
            nothingSelected = {},
            itemSelected = { position ->
                questionTriviaViewModel.difficulty
                    .set(difficulties[position].toLowerCase(Locale.ROOT))
            }
        )

        spinnerType.setupSpinner(
            this@MainMenuActivity,
            types,
            nothingSelected = {},
            itemSelected = { position ->
                questionTriviaViewModel.type
                    .set(types[position].toLowerCase(Locale.ROOT))
            }
        )

        buttonStartQuiz.setOnClickListener {
            questionTriviaViewModel.apply {
                if (
                    !type.get().isNullOrEmpty() &&
                    !difficulty.get().isNullOrEmpty() &&
                    category.get() != 0 && amount.get()!! < 51
                ) {
                    questionTriviaViewModel.retrieveQuestionTrivia()
                } else {
                    Toast.makeText(
                        this@MainMenuActivity,
                        getString(R.string.start_quiz_invalid_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionTriviaViewModel = ViewModelProviders
            .of(this, providerFactory)
            .get(QuestionTriviaViewModel::class.java)

        setContentView(R.layout.activity_main_menu)

        initUI()

        // loading trigger
        questionTriviaViewModel.isLoading.observe(this, Observer { loading ->
            if (loading) loadingDialog.show()
            else loadingDialog.dismiss()
        })

        questionTriviaViewModel.questionTriviaSuccess.observe(
            this@MainMenuActivity,
            Observer { questions ->
                if (questions == null) return@Observer
                else if (questions.results.isNotEmpty()) startActivity(
                    Intent(
                        this@MainMenuActivity,
                        QuestionActivity::class.java
                    ).putExtra("Questions", questions)
                )
            })
    }
}