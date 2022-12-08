package com.example.newsapplication.main_activity.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.main_activity.view_model.MainActivityViewModel
import com.example.newsapplication.main_activity.view_model.MainActivityViewModelFactory
import com.example.newsapplication.model.NewsModel
import com.example.newsapplication.model.Repository
import com.example.newsapplication.network.ApiClient
import com.example.newsapplication.news_details_activity.view.NewsDetailsActivity
import com.example.newsapplication.splash_screen.SplashScreenActivity
import com.example.newsapplication.utils.Constants
import com.example.newsapplication.utils.MyPreference
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, OnNewClickListener,
    ShareButtonClickListener {
    lateinit var viewModel: MainActivityViewModel
    lateinit var viewModelFactory: MainActivityViewModelFactory
    lateinit var dialog: Dialog
    lateinit var preference: MyPreference
    lateinit var themeMode: String


    lateinit var binding: ActivityMainBinding
    lateinit var newsModel: NewsModel.Article


    private lateinit var newsRecycleViewAdapter: NewsRecycleViewAdapter

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = MyPreference.getInstance(this)!!
        themeMode = preference.getData(Constants.THEMETYPE)!!
        if (themeMode.equals("dark")) {
            binding.themeSwitch.setImageResource(R.drawable.light_image)
        } else {
            binding.themeSwitch.setImageResource(R.drawable.dark_image)

        }



        newsRecycleViewAdapter = NewsRecycleViewAdapter(arrayListOf(), this, this, this)
        binding.newsRecycleView.adapter = newsRecycleViewAdapter

        viewModelFactory =
            MainActivityViewModelFactory(Repository.getRepoInstance(ApiClient.getInstance()!!))
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel.getNewsFromRemoteSource("general")



        viewModel.liveDataOfNews.observe(this) {
            if (!it.articles.isNullOrEmpty()) {
                setUpperLayout(it.articles[0])
                Log.i("result", it.toString())
                newsModel = it.articles[0]!!

                newsRecycleViewAdapter.setListOfNews(it.articles.subList(1, it.articles.size - 1))
            } else {
                Log.i("result", "null no data")

            }
        }
        viewModel.loading.observe(this) {
            if (it) {
                binding.topLayout.visibility = View.GONE
                binding.bottomLayout.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.topLayout.visibility = View.VISIBLE
                binding.bottomLayout.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
            }
        }



        setCategoryTaps()
        binding.categoryTabLayout.addOnTabSelectedListener(this)

        binding.topLayout.setOnClickListener {
            val intent = Intent(this, NewsDetailsActivity::class.java)
            intent.putExtra("new", newsModel)
            startActivity(intent)
        }


        ///themeSwitch.isChecked = true
        binding.themeSwitch.setOnClickListener {
            if (themeMode.equals("dark")) {
                showDialog("light")

            } else {
                showDialog("dark")

            }

        }


    }

    private fun setUpperLayout(article: NewsModel.Article?) {
        binding.topLayoutNewsTitle.text = article?.title
        Picasso.get().load(article?.urlToImage).fit().centerCrop().into(binding.topLayoutNewsImage)


    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        newsRecycleViewAdapter.clearNewsList()
        viewModel.getNewsFromRemoteSource(tab?.text.toString().lowercase())
        binding.topLayoutNewsTitle.text = ""
        binding.topLayoutNewsImage.setImageResource(0)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }


    override fun onClick(newsModel: NewsModel.Article) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        intent.putExtra("new", newsModel)
        startActivity(intent)
    }

    override fun onShareButtonClickListener(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("Text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(intent, ""))
    }

    private fun setCategoryTaps() {
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.general))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.business))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.entertainment))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.health))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.science))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.sports))
        binding.categoryTabLayout.addTab(binding.categoryTabLayout.newTab()
            .setText(R.string.technology))
    }


    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun recreate() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showDialog(msg: String) {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)
        val dialogText = dialog.findViewById<TextView>(R.id.theme_type)
        val dialogImage = dialog.findViewById<ImageView>(R.id.theme_img)
        val cancelButton = dialog.findViewById<Button>(R.id.cancel_button)
        val activeButton = dialog.findViewById<Button>(R.id.active_button)

        if (themeMode.equals("dark")) {
            dialogImage.setImageResource(R.drawable.light_image)
            dialogText.text = "Light"
        } else {
            dialogImage.setImageResource(R.drawable.dark_image)
            dialogText.text = "Dark"

        }
        cancelButton.setOnClickListener {
            dialog.dismiss()

        }
        activeButton.setOnClickListener {
            preference.saveData(Constants.THEMETYPE, msg)
            if (msg.equals("light")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }
            val intent = Intent(this, SplashScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)


    }
}