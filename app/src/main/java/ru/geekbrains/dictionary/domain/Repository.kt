package ru.geekbrains.dictionary.domain

import io.reactivex.Observable

interface Repository<T> {

    fun getData(word: String): Observable<T>
}