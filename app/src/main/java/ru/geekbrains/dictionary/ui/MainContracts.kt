package ru.geekbrains.dictionary.ui

import ru.geekbrains.dictionary.data.AppState

class MainContracts {
    interface View {
        fun renderData(appState: AppState)
    }

    interface Presenter<T : AppState, V : View> {
        fun onAttach(view: V)
        fun onDetach(view: V)
        fun getData(word: String, isOnline: Boolean)
    }
}