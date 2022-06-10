package ru.geekbrains.dictionary.domain

interface DataSource<T> {

    suspend fun getData(word: String): T
}