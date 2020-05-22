package organicwaves.truthtables

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private var numberOfVariables = 2
    private var variables = mutableListOf<Button>()
    private var operators = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Begin UI buttons listeners
        btnEvaluate.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnEraseCharacter.setOnClickListener(this)
        // End UI buttons listeners

        // Begin operators buttons listeners
        operators.add(OpLeft)
        operators.add(OpRight)
        operators.add(OpNot)
        operators.add(OpAnd)
        operators.add(OpOr)
        operators.add(OpNand)
        operators.add(OpNor)
        operators.add(OpXor)

        for(operatorButton in operators) {
            operatorButton.setOnClickListener(this)
        }
        // End operators buttons listeners

        // Begin variables buttons listeners
        variables.add(btnA)
        variables.add(btnB)
        variables.add(btnC)
        variables.add(btnD)
        variables.add(btnE)
        variables.add(btnF)
        variables.add(btnX)
        variables.add(btnY)
        variables.add(btnZ)
        variables.add(btnW)

        for (variableButton in variables) {
            variableButton.setOnClickListener(this)
        }
        // Begin variables buttons listeners

        // Begin Spinner init
        val numberVariables = arrayOf(1,2,3,4,5,6,7,8,9,10)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, numberVariables)
        spnNumberOfVariables.adapter = adapter
        spnNumberOfVariables.onItemSelectedListener = this
        lblFunction.setOnTouchListener(this)
        spnNumberOfVariables.setSelection(1)
        // End Spinner init
    }

    override fun onClick(source: View) {
        source.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.abc_grow_fade_in_from_bottom))
        when(source.id){
            R.id.btnClear -> lblFunction.setText("")
            R.id.btnEvaluate -> {
                val nac = Intent(this, ShowResults::class.java)
                nac.putExtra("function",lblFunction.text.toString())
                nac.putExtra("numberOfVariables",(spnNumberOfVariables.selectedItemPosition+1).toString())
                startActivity(nac)
            }
            R.id.btnEraseCharacter ->{
                if (lblFunction.text.toString() != ""){
                    lblFunction.setText(lblFunction.text.toString().substring(0,lblFunction.text.toString().length-1))
                }
            }
            R.id.OpNot -> {
                lblFunction.setText("${lblFunction.text}~")
            }
            R.id.OpAnd -> {
                lblFunction.setText("${lblFunction.text}·")
            }
            R.id.OpNand -> {
                lblFunction.setText("${lblFunction.text}&")
            }
            R.id.OpOr -> {
                lblFunction.setText("${lblFunction.text}+")
            }
            R.id.OpNor -> {
                lblFunction.setText("${lblFunction.text}N")
            }
            R.id.OpXor -> {
                lblFunction.setText("${lblFunction.text}⊕")
            }
            R.id.OpXnor -> {
                lblFunction.setText("${lblFunction.text}X")
            }
            else -> {
                val clickedButton = findViewById<Button>(source.id)
                lblFunction.setText("${lblFunction.text}${clickedButton.text}")
            }
        }
        lblFunction.setSelection(lblFunction.text.toString().length)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        numberOfVariables = p2 + 1
        for (i in 0..9){
            variables[i].isEnabled = false
            variables[i].setBackgroundColor(Color.parseColor("#708777"))
        }
        for (i in 0..p2){
            variables[i].isEnabled = true
            variables[i].setBackgroundColor(Color.parseColor("#445248"))
        }
        lblFunction.setText("")
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var inType = lblFunction.inputType
        lblFunction.inputType = InputType.TYPE_NULL
        lblFunction.onTouchEvent(event)
        lblFunction.inputType = inType
        return true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}