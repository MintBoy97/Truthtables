package organicwaves.truthtables

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private var NumberOfVariables = 2
    private var Variables = mutableListOf<Button>()

    private lateinit var btnDisjunction: Button
    private lateinit var btnConjunction: Button
    private lateinit var btnConditional: Button
    private lateinit var btnBiconditional: Button
    private lateinit var btnNegation: Button
    private lateinit var btnP: Button
    private lateinit var btnQ: Button
    private lateinit var btnR: Button
    private lateinit var btnS: Button
    private lateinit var btnT: Button
    private lateinit var btnU: Button
    private lateinit var btnV: Button
    private lateinit var btnW: Button
    private lateinit var btnX: Button
    private lateinit var btnY: Button
    private lateinit var btnEvaluate: Button
    private lateinit var btnLeftParen: Button
    private lateinit var btnRightParen: Button
    private lateinit var btnClear: Button
    private lateinit var btnEraseCharacter: ImageButton
    private lateinit var lblFunction: TextView
    private lateinit var spnNumberOfVariables: Spinner

    private var alerts = alertsToShow(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lblFunction = findViewById(R.id.lblFunction)
        btnConjunction = findViewById(R.id.OpConjunction)
        btnDisjunction = findViewById(R.id.OpDisjunction)
        btnConditional = findViewById(R.id.OpConditional)
        btnBiconditional = findViewById(R.id.OpBiconditional)
        btnNegation  = findViewById(R.id.OpNegation)
        btnRightParen = findViewById(R.id.OpRight)
        btnLeftParen = findViewById(R.id.OpLeft)
        btnEvaluate  = findViewById(R.id.btnEvaluate)
        btnClear = findViewById(R.id.btnClear)
        btnEraseCharacter = findViewById(R.id.btnEraseCharacter)
        spnNumberOfVariables = findViewById(R.id.spnNumberOfVariables)
        btnP  = findViewById(R.id.OpP)
        btnQ  = findViewById(R.id.OpQ)
        btnR  = findViewById(R.id.OpR)
        btnS  = findViewById(R.id.OpS)
        btnT  = findViewById(R.id.OpT)
        btnU  = findViewById(R.id.OpU)
        btnV  = findViewById(R.id.OpV)
        btnW  = findViewById(R.id.OpW)
        btnX  = findViewById(R.id.OpX)
        btnY  = findViewById(R.id.OpY)

        btnConjunction.setOnClickListener(this)
        btnDisjunction.setOnClickListener(this)
        btnConditional.setOnClickListener(this)
        btnBiconditional.setOnClickListener(this)
        btnNegation.setOnClickListener(this)
        btnRightParen.setOnClickListener(this)
        btnLeftParen.setOnClickListener(this)
        btnEvaluate.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnEraseCharacter.setOnClickListener(this)
        btnP.setOnClickListener(this)
        btnQ.setOnClickListener(this)
        btnR.setOnClickListener(this)
        btnS.setOnClickListener(this)
        btnT.setOnClickListener(this)
        btnU.setOnClickListener(this)
        btnV.setOnClickListener(this)
        btnW.setOnClickListener(this)
        btnX.setOnClickListener(this)
        btnY.setOnClickListener(this)

        Variables.add(0,btnP)
        Variables.add(1,btnQ)
        Variables.add(2,btnR)
        Variables.add(3,btnS)
        Variables.add(4,btnT)
        Variables.add(5,btnU)
        Variables.add(6,btnV)
        Variables.add(7,btnW)
        Variables.add(8,btnX)
        Variables.add(9,btnY)

        //Init Spinner
        val NumberVariables = arrayOf(1,2,3,4,5,6,7,8,9,10)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, NumberVariables)
        spnNumberOfVariables.adapter = adapter
        spnNumberOfVariables.onItemSelectedListener = this

    }

    override fun onClick(source: View) {
        source.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.abc_grow_fade_in_from_bottom))
        when(source.id){
            R.id.btnClear -> lblFunction.text = ""
            R.id.btnEraseCharacter -> {
                if (lblFunction.text != ""){
                    lblFunction.text = "${lblFunction.text}".substring(0,lblFunction.text.length-1)
                }
            }
            R.id.OpNegation -> lblFunction.text = "${lblFunction.text}'"
            R.id.btnEvaluate -> {
                val nac = Intent(this, ShowResults::class.java)
                nac.putExtra("function",lblFunction.text)
                nac.putExtra("numberOfVariables",(spnNumberOfVariables.selectedItemPosition+1).toString())
                startActivity(nac)
            }
            else -> {
                val clickedButton = findViewById<Button>(source.id)
                lblFunction.text = "${lblFunction.text}${clickedButton.text}"
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        NumberOfVariables = p2 + 1
        for (i in 0..9){
            Variables.get(i).isEnabled = false
            Variables.get(i).setBackgroundColor(Color.parseColor("#708777"))
        }
        for (i in 0..p2){
            Variables.get(i).isEnabled = true
            Variables.get(i).setBackgroundColor(Color.parseColor("#445248"))
        }
        lblFunction.text = ""
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}