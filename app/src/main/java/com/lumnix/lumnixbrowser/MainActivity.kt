package com.lumnix.lumnixbrowser


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.lumnix.lumnixbrowser.OtherActivities.About
import com.lumnix.lumnixbrowser.OtherActivities.Feature
import com.lumnix.lumnixbrowser.OtherActivities.Settings
import com.rbddevs.splashy.Splashy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.web_view_fragment.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottomUrlBar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout,HomePage.newInstance(urlBar),"Homepage").commit()

         Splashy(this)
            .setLogo(R.drawable.ic_linux)
            .setTitle("Apex Browser")
            .setTitleSize(20F)
            .setSubTitle("Bring Power Back In Your Hand")
            .setSubTitleColor("#d5d5d5")
            .setTitleColor("#f5f5f5")
            .setProgressColor("#f5f5f5")
            .setLogoScaleType(ImageView.ScaleType.CENTER_CROP)
            .setFullScreen(true)
            .setBackgroundColor("#76678D")
            .setDuration(1000)
            .show()

        fun shiftFocusFromToolBarToBrowser(v:View){
            val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val url = urlBar.text.toString()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,WebViewFragment.newInstance(),"WebView").commitNow()
            webHolder.loadUrl(url)
            webHolder.requestFocus()
            imm.hideSoftInputFromWindow(v.windowToken,0)
        }


        homeBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentLayout,HomePage.newInstance(urlBar),"HomePage")
                .addToBackStack(null).commit()
            urlBar.clearFocus()
        }
        backBtn.setOnClickListener {backAction()}

        urlBar.setOnEditorActionListener{ v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){ 
                shiftFocusFromToolBarToBrowser(v)
                true
            }else{
                false
            }
        }

        fun changeLayout(bool:Boolean){
            if(bool){
                homeBtn.visibility = View.GONE
                backBtn.visibility = View.GONE
            }else{
                homeBtn.visibility = View.VISIBLE
                backBtn.visibility = View.VISIBLE
            }
        }

        urlBar.onFocusChangeListener = View.OnFocusChangeListener{ _, b -> changeLayout(b) }
    }

    private fun backAction(){
        val vibe:Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (supportFragmentManager.findFragmentById(R.id.fragmentLayout)!!.tag == "WebView"  ){
            if (webHolder.canGoBack()){
                webHolder.goBack()
            }else{
                supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,HomePage.newInstance(urlBar),"HomePage").commitNow()
                urlBar.setText("")
                urlBar.clearFocus()
            }
        }else{
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Setting -> {
                val i = Intent(this,
                    Settings::class.java)
                startActivity(i)
                return true
            }
            R.id.About -> {
                val i = Intent(this,
                    About::class.java)
                startActivity(i)
                return true
            }
            R.id.Features -> {
                val i = Intent(this,
                    Feature::class.java)
                startActivity(i)
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {backAction()}

    override fun onPause() {
        super.onPause()
        val pref = application.applicationContext.getSharedPreferences(application.packageName,Activity.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("lastOpenFrag",supportFragmentManager.findFragmentById(R.id.fragmentLayout)!!.tag)
        edit.putString("lastUrl",urlBar.text.toString()).apply()
    }

    override fun onResume() {
        super.onResume()
        val pref = application.applicationContext.getSharedPreferences(application.packageName,Activity.MODE_PRIVATE)
        val s = pref.getString("lastOpenFrag","")
        if (s == "WebView"){
            val url = pref.getString("lastUrl","")
            supportFragmentManager.beginTransaction().add(R.id.fragmentLayout,WebViewFragment.newInstance(),"WebView").commitNow()
            webHolder.loadUrl(url.toString())
        }else{
            supportFragmentManager.beginTransaction().add(R.id.fragmentLayout,HomePage.newInstance(urlBar),"HomePage").commitNow()
        }
    }
}


