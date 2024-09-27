package com.yarets.binom.data

import com.yarets.binom.R
import com.yarets.binom.data.entity.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object DataManagerTest {
    private val persons: List<PersonEntity> = listOf(
        PersonEntity(0, "Джон", R.drawable.john, 38.853316, 58.048813),
        PersonEntity(1, "Сара", R.drawable.sarah, 38.851465, 58.049285),
        PersonEntity(2, "Голлум", R.drawable.gollum, 38.854177, 58.049401)
    )

    fun fetchAllPersons(): Flow<List<PersonEntity>> = flow {
        emit(persons)
    }.flowOn(Dispatchers.IO)

    fun fetchPersonById(id: Int): Flow<PersonEntity> = flow {
        emit(findPersonById(id))
    }.flowOn(Dispatchers.IO)

    private fun findPersonById(id: Int): PersonEntity {
        var result: PersonEntity? = null
        persons.forEach {
            if (it.id == id) result = it
        }
        return result ?: throw IllegalStateException()
    }
}