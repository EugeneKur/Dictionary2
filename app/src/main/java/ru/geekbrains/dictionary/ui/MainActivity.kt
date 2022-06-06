package ru.geekbrains.dictionary.ui

import android.graphics.Color.*
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.databinding.ActivityMainBinding

class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding

    override val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }
    private val observer = Observer<AppState> { renderData(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchButtonActivityMain.setOnClickListener {
            var word = binding.wordEditText.text.toString()
            model.getData(word, true).observe(this@MainActivity, observer)
        }
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
}