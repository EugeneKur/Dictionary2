package ru.geekbrains.dictionary.data

import ru.geekbrains.dictionary.domain.DataSourceLocal
import ru.geekbrains.dictionary.domain.RepositoryLocal

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}