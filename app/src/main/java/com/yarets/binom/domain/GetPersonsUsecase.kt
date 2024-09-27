package com.yarets.binom.domain

import javax.inject.Inject

class GetPersonsUsecase @Inject constructor(val repository: AppRepository) {
    suspend operator fun invoke() = repository.getAllPersons()
    suspend operator fun invoke(id: Int) = repository.getPersonById(id)
}