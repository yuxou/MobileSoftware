package ddwu.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    val aboutBinding by lazy {
        ActivityAboutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aboutBinding.root)

        aboutBinding.btnBack.setOnClickListener {
            finish()
        }

    }

}