package com.example.myapplicationui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.random.Random
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView


class MainActivity() : AppCompatActivity(), View.OnClickListener {
    //TextView
    lateinit var textScore: TextView
    lateinit var scoreResult: TextView
    lateinit var operator: TextView
    lateinit var num1: TextView
    lateinit var num2: TextView
    lateinit var answer: EditText

    //Buttons
    lateinit var btnAdd: Button
    lateinit var btnSubstract: Button
    lateinit var btnMultiply: Button
    lateinit var btnDivide: Button
    lateinit var btnShowAnswer: Button
    lateinit var btnAddLevel: ImageButton
    lateinit var btnResetLevel: ImageButton
    lateinit var showCorrectAnswer: TextView
    lateinit var level1: TextView
    lateinit var btnChack: ImageButton

    //Varianles
    var score: Int = 0
    var correctAnswer: Int = 0
    var playerAnswer: Int=0
    var level: Int = 1
    var A: Int = 0
    var B: Int = 0
    var currentAction : MathActionType = MathActionType.ADD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreResult = findViewById(R.id.score_result)
        btnShowAnswer = findViewById(R.id.btnShowAnswer)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubstract = findViewById(R.id.btnSubstract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        textScore = findViewById(R.id.score_title)
        level1 = findViewById(R.id.level)
        operator = findViewById(R.id.operator)
        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
        btnAddLevel = findViewById(R.id.btnAddLevel)
        btnResetLevel = findViewById(R.id.btnRestLevel)
        showCorrectAnswer = findViewById(R.id.correctAnswerView)
        answer = findViewById(R.id.user_answer)
        btnChack = findViewById(R.id.check_button)
        btnAdd.setOnClickListener(this)
        btnSubstract.setOnClickListener(this)
        btnShowAnswer.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivide.setOnClickListener(this)
        level1.setOnClickListener(this)
        btnResetLevel.setOnClickListener(this)
        btnAddLevel.setOnClickListener(this)
        btnChack.setOnClickListener(this)

        startGame()

    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        val rand = Random(System.nanoTime())
        return (start..end).random(rand)
    }

    fun endNNumber(level: Int): Int {
        return level * 10
    }

    fun updateScore():Boolean {
        var result: String=answer.text.toString()
        if(result==correctAnswer.toString()) {
            score++
            scoreResult.text = score.toString()
            return true
        } else {
            score--
            scoreResult.text = score.toString()
            return false
        }


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnAdd -> { chooseAdd() }
            R.id.btnSubstract ->{ chooseSubtract() }
            R.id.btnDivide ->{ chooseDivide() }
            R.id.btnMultiply ->{ chooseMultiply() }
            R.id.btnAddLevel ->{ addLevel() }
            R.id.btnRestLevel ->{ resetLevel() }
            R.id.btnShowAnswer ->{ showAnswer() }
            R.id.check_button -> { onUserAnswer() }
        }
    }

    private fun startGame() {
        startNextExt()
        answer.requestFocus()
    }

    private fun startNextExt() {
        when (currentAction) {
            MathActionType.ADD -> chooseAdd()
            MathActionType.SUBTRACT -> chooseSubtract()
            MathActionType.MULTIPLE -> chooseMultiply()
            MathActionType.DIVIDE -> chooseDivide()
        }
    }

    private fun chooseAdd() {
        clearAnswerView()

        val start: Int = 0
        val end: Int = endNNumber(level)

        operator.setText("+")
        A = rand(start, end)
        if (A != end) {
            while (B <= end) {
                B = rand(start, end)
                if ((A + B) <= end) {
                    correctAnswer = A + B
                    break
                }

            }
            num1.setText(A.toString())
            num2.setText(B.toString())

            currentAction = MathActionType.ADD
        }
    }

    private fun chooseSubtract() {
        clearAnswerView()

        val start: Int = 0
        val end: Int = endNNumber(level)

        operator.setText("-")
        A = rand(start, end)
        if (A != end) {
            B = rand(start, A)
            correctAnswer = A - B
        }
        num1.setText(A.toString())
        num2.setText(B.toString())

        currentAction = MathActionType.SUBTRACT
    }

    private fun chooseDivide(){
        clearAnswerView()

        val start: Int = 0
        val end: Int = endNNumber(level)

        showCorrectAnswer.setText("")
        operator.setText("/")
        A = rand(start, end)
        var finish: Boolean=true
        while (finish) {
            B = rand(1, A)
            correctAnswer = A / B
            if((A.rem(B))==0){
                finish=false
            }
        }
        num1.setText(A.toString())
        num2.setText(B.toString())

        currentAction = MathActionType.DIVIDE
    }

    private fun chooseMultiply() {
        clearAnswerView()

        val start: Int = 0
        val end: Int = endNNumber(level)

        showCorrectAnswer.setText("")
        operator.setText("*")
        A = rand(start, end)
        var finish: Boolean=true
        while (finish) {
            B = rand(0, end)
            correctAnswer = A * B
            if((A*B)<=end){
                finish=false
            }
        }
        num1.setText(A.toString())
        num2.setText(B.toString())

        currentAction = MathActionType.MULTIPLE
    }

    private fun clearAnswerView(){
        showCorrectAnswer.setText("")
        answer.setText("")
        answer.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun addLevel() {
        showCorrectAnswer.text = ""
        level++
        level1.text = level.toString()
    }

    private fun resetLevel() {
        level = 1
        level1.text = level.toString()
    }

    private fun showAnswer() {
        showCorrectAnswer.text = correctAnswer.toString()
    }

    private fun onUserAnswer(){
        val isRightAnswer=updateScore()
        if (isRightAnswer){
            answer.setBackgroundColor(resources.getColor((R.color.teal_200)));
        }else{
            answer.setBackgroundColor(resources.getColor((R.color.light_red)));
        }

        answer.postDelayed ({
            startNextExt()
        }, 500)


    }
}

enum class MathActionType {
    ADD, SUBTRACT, DIVIDE, MULTIPLE
}