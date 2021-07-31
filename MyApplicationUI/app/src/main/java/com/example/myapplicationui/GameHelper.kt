package com.example.myapplicationui

import java.io.Serializable
import kotlin.random.Random

class GameHelper : Serializable {

    var currentAction: MathActionType = MathActionType.ADD
    var level: Int = 1
    var score: Int = 0

    var ext: ExtModel = ExtModel(1, 1, 1)

    fun getNextExt() : ExtModel{
        ext =  getExt(currentAction, level)
        return ext
    }

    fun isRightAnswer(ans: Int): Boolean{
        return ans == this.ext.ans
    }

    private fun getExt(action: MathActionType, level: Int) : ExtModel{
        val start: Int = 0
        val end: Int = endNNumber(level)

        var A = 0
        var B = 0
        var correctAnswer = 0

        when(action) {
            MathActionType.ADD -> {
                A = rand(start, end)
                if (A != end) {
                    while (B <= end) {
                        B = rand(start, end)
                        if ((A + B) <= end) {
                            break
                        }
                    }
                }
                correctAnswer = A + B
            }
            MathActionType.SUBTRACT -> {
                A = rand(start, end)
                if (A != end) {
                    B = rand(start, A)
                }
                correctAnswer = A - B
            }
            MathActionType.DIVIDE -> {
                A = rand(start, end)
                var finish = true
                while (finish) {
                    B = rand(1, A)
                    if((A.rem(B)) == 0){
                        finish = false
                    }
                }
                correctAnswer = A / B
            }
            MathActionType.MULTIPLE -> {
                A = rand(start, end)
                var finish: Boolean=true
                while (finish) {
                    B = rand(0, end)
                    if((A*B)<=end){
                        finish=false
                    }
                }
                correctAnswer = A * B
            }
        }

        return ExtModel(A, B, correctAnswer)
    }

    private fun endNNumber(level: Int): Int {
        return level * 10
    }

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument start: $start end: $end" }
        val rand = Random(System.nanoTime())
        return (start..end).random(rand)
    }

}

data class ExtModel(
    val a: Int,
    val b: Int,
    val ans: Int
): Serializable