package organicwaves.truthtables

import android.widget.TextView
import java.lang.Math.pow

class generateTruthTable {

    private var numberOfVariables = 0
    private var postfixExpression = ""
    private var columns = 0
    private var rows = 0
    private var resultOfTruthTableTable: IntArray = intArrayOf(0)
    private var initialOfTruthTableTable: Array<IntArray> = Array(0, { IntArray(0) })

    constructor(numberOfVariables: Int, postfixExpression: String) {
        this.numberOfVariables = numberOfVariables
        this.postfixExpression = postfixExpression
        resultOfTruthTableTable = evaluatePostfixExpression()
    }

    private fun generateMatrixWhitTruthValues(): Array<IntArray> {
        columns = numberOfVariables
        rows = pow(2.toDouble(), numberOfVariables.toDouble()).toInt()
        initialOfTruthTableTable = Array(columns, { IntArray(rows) })
        var half = rows
        var counter: Int
        var truthValue: Int
        for (i in 0 until columns) {
            half /= 2
            counter = 0
            truthValue = 0
            for (j in 0 until rows) {
                if (counter == half) {
                    counter = 0
                    truthValue = 1 - truthValue
                }
                initialOfTruthTableTable[i][j] = truthValue
                counter++
            }
        }
        return initialOfTruthTableTable
    }

    private fun evaluatePostfixExpression(): IntArray {
        var result = IntArray(1024)
        val initialOfTruthTableTable = generateMatrixWhitTruthValues()
        var charStack = stack()
        var temporalPostfixExpression: String
        for (k in 0 until rows) {
            temporalPostfixExpression = ""
            for (i in 0 until postfixExpression.length) {
                if (getHierarchyOperator(postfixExpression[i]) < 0) {
                    temporalPostfixExpression += initialOfTruthTableTable[chooseColumnForOperand(postfixExpression[i])][k]
                    charStack.push(temporalPostfixExpression[i])
                } else {
                    temporalPostfixExpression += postfixExpression[i]
                    if (getHierarchyOperator(postfixExpression[i]) == 5) {
                        charStack.push((solveOperator(postfixExpression[i], charStack.pop().toInt() - 48, -1) + 48).toChar())
                    } else {
                        charStack.push((solveOperator(postfixExpression[i], charStack.pop().toInt() - 48, charStack.pop().toInt() - 48) + 48).toChar())
                    }
                }
            }
            //println("k$k: ${charStack.pop()}")
            result[k] = charStack.pop().toInt()
        }
        return result
    }

    private fun chooseColumnForOperand(operator: Char): Int {
        return (operator - 'P')
    }

    private fun getHierarchyOperator(operator: Char): Int {
        when (operator) {
            '\'' -> return 5
            '∧' -> return 4
            '∨' -> return 3
            '→' -> return 2
            '↔' -> return 1
        }
        return -1
    }

    private fun solveOperator(operator: Char, var1: Int, var2: Int): Int {
        when (operator) {
            '\'' -> return negation(var1)
            '∧' -> return conjunction(var1, var2)
            '∨' -> return disjunction(var1, var2)
            '→' -> return conditional(var1, var2)
            '↔' -> return biconditional(var1, var2)
        }
        return -1
    }

    private fun negation(var1: Int): Int {
        return 1 - var1
    }

    private fun conjunction(var1: Int, var2: Int): Int {
        return var1 * var2
    }

    private fun disjunction(var1: Int, var2: Int): Int {
        if ((var1 + var2) > 1) {
            return 1
        }
        return var1 + var2
    }

    private fun conditional(var1: Int, var2: Int): Int {
        if (var1 == 0 && var2 == 1) {
            return 0
        }
        return 1
    }

    private fun biconditional(var1: Int, var2: Int): Int {
        if (var1 == var2) {
            return 1
        }
        return 0
    }

    fun printInTextView(tv: TextView, fn: String) {
        var toShow = ""
        for (j in 0 until columns) {
            toShow += "${('P' + j)}  "
        }
        toShow += "$fn\n"
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                toShow += "${initialOfTruthTableTable[j][i]}  "
            }
            toShow += "   ${resultOfTruthTableTable[i].toChar()}\n"
        }
        tv.text = toShow
    }

}