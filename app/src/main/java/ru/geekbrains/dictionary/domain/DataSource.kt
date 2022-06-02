package ru.geekbrains.dictionary.domain

import io.reactivex.Observable

interface DataSource<T> {
    fun getData(word: String): Observable<T>
}