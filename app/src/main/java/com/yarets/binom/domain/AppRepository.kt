package com.yarets.binom.domain

import com.yarets.binom.domain.model.PersonModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAllPersons(): Flow<List<PersonModel>>
    suspend fun getPersonById(id: Int): Flow<PersonModel>
}