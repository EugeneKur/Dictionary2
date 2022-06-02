package ru.geekbrains.dictionary.data

import io.reactivex.Observable
import ru.geekbrains.dictionary.domain.DataSource
import ru.geekbrains.dictionary.domain.Repository

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}