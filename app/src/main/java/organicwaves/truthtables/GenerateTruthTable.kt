package organicwaves.truthtables

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.TypedValue
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.lang.Math.pow
import android.graphics.Paint.Style


class GenerateTruthTable {

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
        var result = IntArray(1024+5)
        val initialOfTruthTableTable = generateMatrixWhitTruthValues()
        var charStack = Stack()
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

    private fun chooseColumnForOperand(operand: Char): Int {
        when(operand) {
            'A' -> return 0
            'B' -> return 1
            'C' -> return 2
            'D' -> return 3
            'E' -> return 4
            'F' -> return 5
            'X' -> return 6
            'Y' -> return 7
            'Z' -> return 8
            'W' -> return 9
        }
        return -1
    }

    private fun getHierarchyOperator(operator:Char): Int {
        when(operator) {
            '~' -> return 5
            '⊕' -> return 4
            'X' -> return 4
            '·' -> return 3
            '&' -> return 3
            '+' -> return 2
            'N' -> return 2
        }
        return -1
    }

    private fun solveOperator(operator: Char, var1: Int, var2: Int): Int {
        when (operator) {
            '~' -> return not(var1)
            '⊕' -> return xor(var1, var2)
            'X' -> return not(xor(var1,var2))
            '·' -> return and(var1, var2)
            '&' -> return not(and(var1, var2))
            '+' -> return or(var1, var2)
            'N' -> return not(or(var1, var2))
        }
        return -1
    }

    private fun not(var1: Int): Int {
        return 1 - var1
    }

    private fun and(var1: Int, var2: Int): Int {
        return var1 * var2
    }

    private fun or(var1: Int, var2: Int): Int {
        if ((var1 + var2) > 1) {
            return 1
        }
        return var1 + var2
    }

    private fun xor(var1: Int, var2: Int): Int {
        if (var1 == var2) {
            return 0
        }
        return 1
    }

    private fun getStrVariable(nVar: Int): Char {
        when (nVar) {
            0 -> return 'A'
            1 -> return 'B'
            2 -> return 'C'
            3 -> return 'D'
            4 -> return 'E'
            5 -> return 'F'
            6 -> return 'X'
            7 -> return 'Y'
            8 -> return 'Z'
            9 -> return 'W'
        }
        return '0'
    }

    fun printInTextView(tv: TextView, fn: String) {
        var toShow = ""
        for (j in 0 until columns) {
            toShow += "${getStrVariable(j)}  "
        }
        toShow += "$fn\n"
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                toShow += "${initialOfTruthTableTable[j][i]}  "
            }
            toShow += "\t${resultOfTruthTableTable[i].toChar()}\n"
        }
        tv.text = toShow
    }

    fun printInTableLayout(tbl: TableLayout, fn: String){
        val context = tbl.context
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        val txtSize = 10f

        // Color of rows
        val colorh = Color.parseColor("#493C44")
        val color1 = Color.parseColor("#dcdfe6")
        val color2 = Color.parseColor("#708777")
        var currentColor = color2

        // Header of the table
        val headerRow = TableRow(context)
        headerRow.layoutParams = layoutParams

        for (j in 0 until columns) {
            headerRow.addView(createStrokeFixedTextView(context, getStrVariable(j).toString(), txtSize, colorh))
        }
        headerRow.addView(createStrokeTextView(context, fn, txtSize, colorh))

        tbl.addView(headerRow)

        // Data of the table
        for (i in 0 until rows) {

            val dataRow = TableRow(context)
            dataRow.layoutParams = layoutParams

            if (i % 4 == 0) {
                if (currentColor == color1) {
                    currentColor = color2
                } else {
                    currentColor = color1
                }
            }

            for (j in 0 until columns) {
                dataRow.addView(createStrokeFixedTextView(context, initialOfTruthTableTable[j][i].toString(), txtSize, currentColor))
            }
            dataRow.addView(createStrokeFixedTextView(context, resultOfTruthTableTable[i].toChar().toString(), txtSize, currentColor))

            tbl.addView(dataRow)
        }
    }

    private fun createStrokeFixedTextView(context: Context, text:String, textSize:Float, color:Int):TextView {
        var tv = TextView(context)
        tv.text = text
        tv.textSize = dpToPx(textSize,context)
        tv.setTextColor(Color.BLACK)
        tv.gravity = Gravity.CENTER
        tv.width = dpToPx(30f,context).toInt()
        tv.setBackgroundColor(color)//falta ver que pex
        // stroke
        val sd = ShapeDrawable()
        sd.shape = RectShape()
        sd.paint.color = Color.BLACK
        sd.paint.strokeWidth = 5f
        sd.paint.style = Style.STROKE
        tv.background = sd
        return tv
    }

    private fun createStrokeTextView(context: Context, text:String, textSize:Float, color:Int):TextView {
        var tv = TextView(context)
        tv.text = text
        tv.textSize = dpToPx(textSize,context)
        tv.setTextColor(Color.BLACK)
        tv.gravity = Gravity.CENTER
        tv.setBackgroundColor(color)//falta ver que pex
        // stroke
        val sd = ShapeDrawable()
        sd.shape = RectShape()
        sd.paint.color = Color.BLACK
        sd.paint.strokeWidth = 5f
        sd.paint.style = Style.STROKE
        tv.background = sd
        return tv
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}