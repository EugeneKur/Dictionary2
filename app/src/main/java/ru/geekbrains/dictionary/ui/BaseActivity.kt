package ru.geekbrains.dictionary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.dictionary.data.AppState

abstract class BaseActivity<T : AppState> : AppCompatActivity(), MainContracts.View {

    protected lateinit var presenter: MainContracts.Presenter<T, MainContracts.View>

    protected abstract fun createPresenter(): MainContracts.Presenter<T, MainContracts.View>

    abstract override fun renderData(appState: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetach(this)
    }
}
