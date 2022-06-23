package ru.geekbrains.dictionary.ui.main

import android.content.Intent
import android.graphics.Color.*
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.geekbrains.dictionary.R
import ru.geekbrains.dictionary.ui.base.BaseActivity
import ru.geekbrains.dictionary.ui.history.HistoryActivity
import ru.geekbrains.dictionary.utils.isOnline

class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding
    protected var isNetworkAvailable: Boolean = false
    override lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniViewModel()
        initViews()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    binding.resultTextView.text = "Response from server is empty"
                    binding.resultTextView.setTextColor(RED)
                } else {
                    binding.resultTextView.text =
                        dataModel[0].meanings?.get(0)?.translation?.translation
                    binding.resultTextView.setTextColor(DKGRAY)
                    setImage(dataModel[0].meanings?.get(0)?.imageUrl)
                }
            }
            is AppState.Loading -> {
                binding.resultTextView.text = "Загрузка..."
                binding.resultTextView.setTextColor(GREEN)
            }
            is AppState.Error -> {
                binding.resultTextView.text = appState.error.message
                binding.resultTextView.setTextColor(RED)
            }
        }
    }

    private fun initViews() {
        binding.searchButtonActivityMain.setOnClickListener {
            var word = binding.wordEditText.text.toString()
            isNetworkAvailable = isOnline(applicationContext)
            model.getData(word, isNetworkAvailable)
        }
        binding.historyButtonActivityMain.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            true
        }
    }

    private fun iniViewModel() {
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> {
            renderData(it)
        })
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get().load("https:$imageLink")
            .placeholder(R.drawable.ic_download).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    imageView.setImageResource(R.drawable.ic_error)
                }
            })
    }

//    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
//        Glide.with(imageView)
//            .load("https:$imageLink")
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    imageView.setImageResource(R.drawable.ic_error)
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//            })
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_error)
//                    .centerCrop()
//            )
//            .into(imageView)
//    }

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(this)
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.ic_error)
                }
            )
            .transformations(
                CircleCropTransformation(),
            )
            .build()
        ImageLoader(this).execute(request)
    }


    private fun setImage(imageLink: String?) {
        if (imageLink.isNullOrBlank()) {
            binding.descriptionImageview.setImageResource(R.drawable.ic_error)
        } else {
            //usePicassoToLoadPhoto(binding.descriptionImageview, imageLink)
            //useGlideToLoadPhoto(binding.descriptionImageview, imageLink)
            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }

}