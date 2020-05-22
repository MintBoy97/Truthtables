package organicwaves.truthtables

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class AlertsToShow {
    private var context:Context
    constructor(context:Context){
        this.context = context
    }
    fun showSimpleAlert(title:String,message:String){
        val builder  = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("ok") { dialogInterface: DialogInterface, i: Int ->
            //finish()
        }
        builder.show()
    }
}