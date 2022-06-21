package ru.geekbrains.dictionary.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.dictionary.R
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.utils.isOnline

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)


}
