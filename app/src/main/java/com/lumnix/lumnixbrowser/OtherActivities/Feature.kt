package com.lumnix.lumnixbrowser.OtherActivities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.lumnix.lumnixbrowser.R
import kotlinx.android.synthetic.main.activity_feature.*

class Feature : AppCompatActivity() {
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Fast Browsing",
                "Enjoy Fast Internet Browsing",
                R.drawable.web
            ),
            IntroSlide(
                "Private Browsing",
                "No Data Stored",
                R.drawable.privacy
            ),
            IntroSlide(
                "Get Latest News",
                "get Latest UnBiased News",
                R.drawable.news
            )
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)
        introSliderViewPager.adapter = introSliderAdapter
        setupIndicator()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == introSliderViewPager.childCount + 1){
                    nextBtn.text = "Go Back"
                }else{
                    nextBtn.text = "Next"
                }
                setCurrentIndicator(position)
            }
        })
        nextBtn.setOnClickListener {
            val vibe: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount){
                introSliderViewPager.currentItem += 1
                vibe.vibrate(100)
            }else{
                vibe.vibrate(200)
                finish();
            }
        }
    }
    private fun setupIndicator(){
        val indicator = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams:LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicator.indices){
         indicator[i] = ImageView(applicationContext)
            indicator[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_inactive)
                )
            this?.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicator[i])
        }
    }
    private fun setCurrentIndicator(index:Int){
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorContainer.get(i) as ImageView
            if(i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}