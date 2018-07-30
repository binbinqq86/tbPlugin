package plugin.tb.com.tbplugin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        tv.text = """ENVIRONMENT:${BuildConfig.ENVIRONMENT}
//app_name:${getString(R.string.app_name)}
//flavor:${BuildConfig.FLAVOR}
//                  """
    }
}
