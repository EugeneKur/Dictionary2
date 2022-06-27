package ru.geekbrains.dictionary.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
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
import ru.geekbrains.dictionary.ui.history.HistoryActivity
import ru.geekbrains.dictionary.ui.history.HistoryInteractor
import ru.geekbrains.dictionary.ui.history.HistoryViewModel
import ru.geekbrains.dictionary.ui.main.MainActivity
import ru.geekbrains.dictionary.ui.main.MainInteractor
import ru.geekbrains.dictionary.ui.main.MainViewModel

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
    scope(named<MainActivity>()) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}

