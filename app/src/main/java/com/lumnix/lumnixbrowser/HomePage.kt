package com.lumnix.lumnixbrowser


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.homepage_layout.*
import kotlin.collections.ArrayList

class HomePage(urlBar: EditText): Fragment(){
    private var mUrlBar:EditText = urlBar
    companion object{
        fun newInstance(urlBar:EditText):HomePage{
            return HomePage(urlBar)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.homepage_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView:RecyclerView = recycle
        val arrayListName:ArrayList<String> = ArrayList()
        val arrayListImage:ArrayList<String> = ArrayList()
        val arrayListDisc:ArrayList<String> = ArrayList()
        val arrayListLink:ArrayList<String> = ArrayList()

        homePageSearch.requestFocus()
        homePageSearch.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val search =  homePageSearch.text.toString()
                mUrlBar.setText(search)
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,WebViewFragment.newInstance(),"WebView")
                    .commitNow()
                true
            }else{
                false
            }
        }
        adder(arrayListName,arrayListImage,arrayListDisc,arrayListLink)
        val mAdapter = CustomRecyclerAdapter(arrayListImage,arrayListName,arrayListDisc,arrayListLink,{
            mUrlBar.setText(it)
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout,WebViewFragment.newInstance(),"WebView")
                .commitNow()
        },context!!)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun adder(arrayListName:ArrayList<String>
                      ,arrayListImage:ArrayList<String>
                      ,arrayListDisc:ArrayList<String>
                      ,arrayListLink:ArrayList<String>)
    {
        arrayListName.add("New Education Policy 2020 ")
        arrayListImage.add("https://images.indianexpress.com/2020/07/New-Education-Policy-759.jpg")
        arrayListDisc.add("HRD Ministry New Education Policy 2020 LIVE updates")
        arrayListLink.add("https://indianexpress.com/article/education/new-education-policy-2020-live-updates-cabinet-approves-nep-ramesh-pokhriyal-prakash-javadekar-6529139/")

        arrayListName.add("Rajasthan Political Crisis LIVE Updates: HC Notice to Speaker")
        arrayListImage.add("https://images.news18.com/ibnlive/uploads/2020/07/1595652669_gehlot-3.jpg?impolicy=website&width=515&height=342")
        arrayListDisc.add("Sources present at the CLP meeting hosted by Gehlot said he added that....")
        arrayListLink.add("https://www.news18.com/news/politics/rajasthan-political-crisis-live-updates-impasse-ends-as-governor-gives-nod-to-assembly-session-from-aug-14-with-covid-19-measures-2743669.html")

        arrayListName.add("Lock down extended in Tamil Nadu till August 31")
        arrayListImage.add("https://c.ndtvimg.com/2019-03/eanet61g_palaniswami-_625x300_06_March_19.jpg")
        arrayListDisc.add("More than 16 firms in the US have committed investments to the tune of over Rs 2,700....")
        arrayListLink.add("https://timesofindia.indiatimes.com/city/chennai/lockdown-extension-in-tamil-nadu-govt-announces-more-relaxations/articleshow/77257479.cms")

        arrayListName.add("Indo-Pacific encouraged by India standing up to China: US NSC official")
        arrayListDisc.add("“Few countries are more familiar with China’s malign influence than India,” US National Security Council official Lisa Curtis said.")
        arrayListImage.add("https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2020/07/30/Pictures/xi-jinping-narendra-modi_0b7b284e-d253-11ea-bae0-701c4bed6ede.jpg")
        arrayListLink.add("https://www.hindustantimes.com/india-news/indo-pacific-encouraged-by-india-standing-up-to-china-us-nsc-official/story-AH9Fcvgn2K7SUR1bKsJt8I.html")
    }


}