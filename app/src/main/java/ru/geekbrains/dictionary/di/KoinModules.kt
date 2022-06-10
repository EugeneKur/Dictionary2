package ru.geekbrains.dictionary.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.dictionary.data.DataModel
import ru.geekbrains.dictionary.data.RepositoryImpl
import ru.geekbrains.dictionary.domain.Repository
import ru.geekbrains.dictionary.domain.RetrofitImpl
import ru.geekbrains.dictionary.domain.RoomDataBaseImpl
import ru.geekbrains.dictionary.ui.MainInteractor
import ru.geekbrains.dictionary.ui.MainViewModel

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(RoomDataBaseImpl()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}
