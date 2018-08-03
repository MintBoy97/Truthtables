package organicwaves.truthtables

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
//for google ads
import com.google.android.gms.ads.*




class ShowResults : AppCompatActivity() {
    private lateinit var lblValues:TextView
    private var alerts = alertsToShow(this)

    //for google ads
    //private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_results)

        //for google ad
        MobileAds.initialize(this, "ca-app-pub-5364668367910017~8666672714")
        /*mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }*/
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
