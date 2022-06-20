package ru.geekbrains.dictionary.domain

import geekbrains.ru.translator.utils.convertDataModelSuccessToEntity
import geekbrains.ru.translator.utils.mapHistoryEntityToSearchResult
import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.domain.room.HistoryDao

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {

        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}

//class RoomDataBaseImpl : DataSource<List<DataModel>> {
//
//    override suspend fun getData(word: String): List<DataModel> {
//        TODO("not implemented")
//    }
//}
