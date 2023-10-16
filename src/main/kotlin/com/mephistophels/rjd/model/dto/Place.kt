package com.mephistophels.rjd.model.dto

class Place(
    var number: Int,
    var user: AbstractUserDto? = null,
    var rating: Double
)

class Carriage(
    var places: List<Place>
)

class Train(
    var carriages: List<Carriage>
)