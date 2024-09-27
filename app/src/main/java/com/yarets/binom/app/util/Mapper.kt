package com.yarets.binom.app.util

import com.yarets.binom.app.model.Person
import com.yarets.binom.domain.model.PersonModel

fun PersonModel.toPerson() = Person(id, name, image, gpsX, gpsY)