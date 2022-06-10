package ru.geekbrains.dictionary.data

import ru.geekbrains.dictionary.domain.DataSource
import ru.geekbrains.dictionary.domain.Repository

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}