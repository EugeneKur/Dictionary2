package ru.geekbrains.dictionary.ui.main

import ru.geekbrains.dictionary.data.AppState
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.domain.Interactor
import ru.geekbrains.dictionary.domain.Repository
import ru.geekbrains.dictionary.domain.RepositoryLocal


class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}

//class MainInteractor(
//    private val remoteRepository: Repository<List<DataModel>>,
//    private val localRepository: Repository<List<DataModel>>
//) : Interactor<AppState> {
//
//    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
//        return AppState.Success(
//            if (fromRemoteSource) {
//                remoteRepository
//            } else {
//                localRepository
//            }.getData(word)
//        )
//    }
//}

