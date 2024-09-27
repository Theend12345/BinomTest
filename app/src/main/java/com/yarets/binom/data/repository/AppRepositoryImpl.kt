package com.yarets.binom.data.repository

import com.yarets.binom.data.DataManagerTest
import com.yarets.binom.data.util.toModel
import com.yarets.binom.domain.AppRepository
import com.yarets.binom.domain.model.PersonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl : AppRepository {
    override suspend fun getAllPersons(): Flow<List<PersonModel>> {
        return DataManagerTest.fetchAllPersons().map { list ->
            list.map { personEntity ->
                personEntity.toModel()
            }
        }
    }

    override suspend fun getPersonById(id: Int): Flow<PersonModel> {
        return DataManagerTest.fetchPersonById(id).map {
            it.toModel()
        }
    }
}