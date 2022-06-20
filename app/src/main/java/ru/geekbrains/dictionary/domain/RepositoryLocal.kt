package ru.geekbrains.dictionary.domain

import ru.geekbrains.dictionary.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}