package ru.geekbrains.dictionary.ui.history

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import ru.geekbrains.dictionary.R
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.databinding.ActivityHistoryBinding
import ru.geekbrains.dictionary.ui.base.BaseActivity
import ru.geekbrains.dictionary.utils.isOnline
import ru.geekbrains.utils.viewById


class HistoryActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityHistoryBinding
    override lateinit var model: HistoryViewModel
    protected var isNetworkAvailable: Boolean = false
    private val historyActivityRecyclerview by viewById<RecyclerView>(R.id.history_activity_recyclerview)

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNetworkAvailable = isOnline(applicationContext)
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        model.getData("", false)
    }

    fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<AppState> { renderData(it) })
    }

    private fun initViews() {
        historyActivityRecyclerview.adapter = adapter
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                appState.data?.let {
                    if (it.isEmpty()) {
                        Toast.makeText(this@HistoryActivity, "Пусто", Toast.LENGTH_SHORT).show()
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                Toast.makeText(this@HistoryActivity, "Загрузка", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                var message = appState.error.message
                Toast.makeText(this@HistoryActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}