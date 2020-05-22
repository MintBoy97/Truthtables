package organicwaves.truthtables

// Google ads
// TableView
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class ShowResults : AppCompatActivity() {
    private var alerts = AlertsToShow(this)

    // Google ad view
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_results)

        // Begin Google ad banner
        MobileAds.initialize(this, "ca-app-pub-5364668367910017~8666672714")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        // End Google ad banner

        val searchObject:Intent = intent
        val numVar:Int = searchObject.getStringExtra("numberOfVariables").toInt()
        val function = searchObject.getStringExtra("function")

        val cn = ChangeNotation(function)
        cn.getPostfixNotation()

        val gen = GenerateTruthTable(numVar, cn.getPostfixNotation())

        //val tblTruth = findViewById(R.id.truthTable) as TableView<*>
        //gen.printInTextView(lblValues,function)
        //gen.printInTableView(tblTruth,function,this)

        /*try {
            val cn = ChangeNotation(function)
            cn.getPostfixNotation()
            try {
                val gen = GenerateTruthTable(numVar, cn.getPostfixNotation())
                gen.printInTextView(lblValues,function)
            } catch (e: Exception){
                alerts.showSimpleAlert("Input Error",getString(R.string.input_error))
                // guardar en bd la que no se pudo
            }
        } catch (e: Exception){
            alerts.showSimpleAlert("Input Error",getString(R.string.internal_error))
        }*/
    }
}
