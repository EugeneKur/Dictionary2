package ru.geekbrains.dictionary.domain

import io.reactivex.Observable
import ru.geekbrains.dictionary.data.DataModel

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}