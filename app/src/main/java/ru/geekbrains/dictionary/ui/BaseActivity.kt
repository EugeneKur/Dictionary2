package ru.geekbrains.dictionary.ui

import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.dictionary.data.AppState

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)
}
