package ru.geekbrains.dictionary.domain

import ru.geekbrains.dictionary.data.DataModel

class RoomDataBaseImpl : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented")
    }
}