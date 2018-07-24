package organicwaves.truthtables

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowResults : AppCompatActivity() {
    private lateinit var lblValues:TextView
    private var alerts = alertsToShow(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_results)

        lblValues = findViewById(R.id.lblValues)

        val searchObject:Intent = intent
        val nov:Int = searchObject.getStringExtra("numberOfVariables").toInt()
        val function = searchObject.getStringExtra("function")
        try {
            val cn = changeNotation(function)
            try {
                val gen = generateTruthTable(nov, cn.getPostfixNotation())
                gen.printInTextView(lblValues,function)
            } catch (e: Exception){
                alerts.showSimpleAlert("Error","Imposible realizar la operacion")
            }
        } catch (e: Exception){
            alerts.showSimpleAlert("Error","Error de notacion")
        }
    }
}
