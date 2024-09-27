package com.yarets.binom.data.util

import com.yarets.binom.data.entity.PersonEntity
import com.yarets.binom.domain.model.PersonModel

fun PersonEntity.toModel() = PersonModel(id, name, image, gpsX, gpsY)