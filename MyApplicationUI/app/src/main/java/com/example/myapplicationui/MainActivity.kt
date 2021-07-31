package com.example.myapplicationui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import kotlin.random.Random
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text


class MainActivity() : AppCompatActivity(), View.OnClickListener {
    //TextView
    lateinit var textScore: TextView
    lateinit var scoreResult: TextView
    lateinit var operator: TextView
    lateinit var num1: TextView
    lateinit var num2: TextView
    lateinit var answer: TextView

    //Buttons
    lateinit var btnAdd: Button
    lateinit var btnSubstract: Button
    lateinit var btnMultiply: Button
    lateinit var btnDivide: Button
    lateinit var btnReset: Button
    lateinit var btnShowAnswer: Button
    lateinit var resetLevel: Button
    lateinit var showCorrectAnswer: TextView
    lateinit var level1: TextView

    //Varianles
    var score: Int = 0
    var correctAnswer: Int = 0
    var playerAnswer: Int=0
    var level: Int = 1
    var A: Int = 0
    var B: Int = 0

    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreResult = findViewById(R.id.scoreResult)
        btnShowAnswer = findViewById(R.id.btnShowAnswer)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubstract = findViewById(R.id.btnSubstract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        btnReset = findViewById(R.id.btnAddLevel)
        textScore = findViewById(R.id.textScore)
        level1 = findViewById(R.id.level1)
        operator = findViewById(R.id.operator)
        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
        resetLevel = findViewById(R.id.restLevel)
        showCorrectAnswer = findViewById(R.id.correctAnswerView)
        answer = findViewById(R.id.editTextNumber2) as TextView


        btnAdd.setOnClickListener(this)
        btnSubstract.setOnClickListener(this)
        btnShowAnswer.setOnClickListener(this)
        btnReset.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivide.setOnClickListener(this)
        level1.setOnClickListener(this)
        resetLevel.setOnClickListener(this)
        answer.setOnClickListener(this)

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
            showCorrectAnswer.setText("")
            answer.setBackgroundColor(Color.GREEN);
            return true
        }
        answer.setBackgroundColor(Color.GREEN);
        return false

    }

    override fun onClick(v: View) {
        var start: Int = 0
        var end: Int = endNNumber(level)

        when (v.id) {

            R.id.btnAdd -> {

                showCorrectAnswer.setText("")
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
                    answer.text = ""
                }
            }
            R.id.btnSubstract ->{
                showCorrectAnswer.setText("")
                operator.setText("-")
                A = rand(start, end)
                if (A != end) {
                    B = rand(start, A)
                    correctAnswer = A - B
                }
                    num1.setText(A.toString())
                    num2.setText(B.toString())
                    answer.text = ""

            }
            R.id.btnDivide ->{
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
                answer.text = ""

            }
            R.id.btnMultiply ->{
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
                answer.text = ""

            }
            R.id.btnAddLevel ->{
                    showCorrectAnswer.setText("")
                    level++
                    level1.text = level.toString()
            }
            R.id.restLevel ->{
                    level = 1
                    level1.text = "1"
            }

            R.id.btnShowAnswer ->{
                    showCorrectAnswer.setText(correctAnswer.toString())
            }

            R.id.editTextNumber2 ->{
                var changeColor:Boolean=updateScore()
                if (changeColor==false){
                    answer.setBackgroundColor(Color.RED);
                }else{
                    answer.setBackgroundColor(Color.GREEN);
                }
            }

        }

    }
}