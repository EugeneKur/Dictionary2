package ru.geekbrains.dictionary.ui

import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.domain.Interactor
import ru.geekbrains.dictionary.domain.Repository


class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}
