package ru.geekbrains.dictionary.domain


interface Repository<T> {

    suspend fun getData(word: String): T
}