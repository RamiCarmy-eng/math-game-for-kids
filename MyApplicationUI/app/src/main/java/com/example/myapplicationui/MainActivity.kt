package com.example.myapplicationui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationui.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    //Varianles
    var gameHelper: GameHelper = GameHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            btnAdd.setOnClickListener(this@MainActivity)
            btnSubstract.setOnClickListener(this@MainActivity)
            btnShowAnswer.setOnClickListener(this@MainActivity)
            btnMultiply.setOnClickListener(this@MainActivity)
            btnDivide.setOnClickListener(this@MainActivity)
            level.setOnClickListener(this@MainActivity)
            btnRestLevel.setOnClickListener(this@MainActivity)
            btnAddLevel.setOnClickListener(this@MainActivity)
            checkButton.setOnClickListener(this@MainActivity)
        }

        if(savedInstanceState == null) {
            startGame()
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putSerializable("GameHelper", gameHelper)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        gameHelper = savedInstanceState.getSerializable("GameHelper") as GameHelper
        updateFields()
    }

    private fun updateFields() {
        binding.operator.text = when(gameHelper.currentAction) {
            MathActionType.ADD -> "+"
            MathActionType.SUBTRACT -> "-"
            MathActionType.MULTIPLE -> "*"
            MathActionType.DIVIDE -> "/"
        }
        binding.num1.text = gameHelper.ext.a.toString()
        binding.num2.text = gameHelper.ext.b.toString()

        binding.scoreResult.text = gameHelper.score.toString()

        binding.level.text = gameHelper.level.toString()
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
        getNextAnswer()
        binding.userAnswer.requestFocus()
    }

    private fun chooseAdd() {
        gameHelper.currentAction = MathActionType.ADD
        getNextAnswer()
    }

    private fun chooseSubtract() {
        gameHelper.currentAction = MathActionType.SUBTRACT
        getNextAnswer()
    }

    private fun chooseDivide(){
        gameHelper.currentAction = MathActionType.DIVIDE
        getNextAnswer()
    }

    private fun chooseMultiply() {
        gameHelper.currentAction = MathActionType.MULTIPLE
        getNextAnswer()
    }

    private fun getNextAnswer() {
        clearAnswerView()
        gameHelper.getNextExt()
        updateFields()
    }

    private fun clearAnswerView(){
        binding.correctAnswerView.setText("")
        binding.userAnswer.setText("")
        binding.userAnswer.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun addLevel() {
        gameHelper.level = gameHelper.level + 1
        updateFields()
    }

    private fun resetLevel() {
        gameHelper.level = 1
        updateFields()
    }

    private fun showAnswer() {
        binding.correctAnswerView.text = gameHelper.ext.ans.toString()
    }

    private fun onUserAnswer(){
        if(binding.userAnswer.text.isNullOrEmpty()) return

        val isRightAnswer=gameHelper.isRightAnswer(binding.userAnswer.text.toString().toInt())

        if(isRightAnswer) {
            gameHelper.score = gameHelper.score + 1
        } else {
            gameHelper.score = gameHelper.score - 1
        }

        updateFields()

        if (isRightAnswer){
            binding.userAnswer.setBackgroundColor(resources.getColor((R.color.teal_200)));
        }else{
            binding.userAnswer.setBackgroundColor(resources.getColor((R.color.light_red)));
        }

        binding.userAnswer.postDelayed ({
            getNextAnswer()
        }, 500)
    }


}

enum class MathActionType {
    ADD, SUBTRACT, DIVIDE, MULTIPLE
}