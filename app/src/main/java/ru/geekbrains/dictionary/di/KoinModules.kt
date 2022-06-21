package ru.geekbrains.dictionary.di

import androidx.room.Room
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.data.RepositoryImpl
import ru.geekbrains.dictionary.data.RepositoryLocalImpl
import ru.geekbrains.dictionary.domain.Repository
import ru.geekbrains.dictionary.domain.RepositoryLocal
import ru.geekbrains.dictionary.domain.RetrofitImpl
import ru.geekbrains.dictionary.domain.RoomDataBaseImpl
import ru.geekbrains.dictionary.domain.room.HistoryDataBase
import ru.geekbrains.dictionary.ui.history.HistoryInteractor
import ru.geekbrains.dictionary.ui.history.HistoryViewModel
import ru.geekbrains.dictionary.ui.main.MainInteractor
import ru.geekbrains.dictionary.ui.main.MainViewModel

//val application = module {
//    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
//    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(RoomDataBaseImpl()) }
//}
//
//val mainScreen = module {
//    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
//    factory { MainViewModel(get()) }
//}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java,
        "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> {
        RepositoryImpl(RetrofitImpl()) }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryLocalImpl(RoomDataBaseImpl(get()))
    }
}
val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}
val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}

