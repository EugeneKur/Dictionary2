package ru.geekbrains.dictionary.domain

import io.reactivex.Observable
import ru.geekbrains.dictionary.data.DataModel

class RoomDataBaseImpl : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}