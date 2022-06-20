package ru.geekbrains.dictionary.domain

import ru.geekbrains.dictionary.data.AppState

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}