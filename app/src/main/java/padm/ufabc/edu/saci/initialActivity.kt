package padm.ufabc.edu.saci

import android.content.Intent
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

import kotlinx.android.synthetic.main.activity_initial.*

class initialActivity : AppCompatActivity() {

    val TAG = "InitialActivity"
    val ERROR_DIALOG_REQUEST = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        //setSupportActionBar(toolbar)

        if(isServicesOk()) {
            init()
        }

    }

    private fun init() {
        val btnOpenMap = findViewById<Button>(R.id.open_map_button)
        btnOpenMap.setOnClickListener(View.OnClickListener {
            @Override
            fun OnClickAction(view: View) {
                startActivity(Intent(this@initialActivity, MapsActivity::class.java))
            }
        })
    }

    public fun isServicesOk(): Boolean {
        Log.d(TAG, "isServicesOK: Checking google services version")
        var availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if(availability == ConnectionResult.SUCCESS) {
            // Tudo esta ok. Usuario pode usar maps
            Log.d(TAG, "isServicesOK: Google play services is working")
            return true
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(availability)) {
            Log.d(TAG, "isServicesOK: an error ocurred but we can fix it")
            var dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, availability, ERROR_DIALOG_REQUEST)
            dialog.show()
        } else {
            Toast.makeText(this, "We cannot make map request", Toast.LENGTH_SHORT).show()
        }
        return false
    }

}
