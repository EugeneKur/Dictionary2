package ru.geekbrains.dictionary.ui.base


interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}