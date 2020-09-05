package com.lumnix.lumnixbrowser.OtherActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.lumnix.lumnixbrowser.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.web_view_fragment.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_settings
        )

    }
}