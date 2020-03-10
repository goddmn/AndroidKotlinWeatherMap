package com.example.weatherappkotlin.utils

interface Communicator {
    fun passCoord(lat: Double, lon: Double)
    fun passCoordFromMap(lat: Double, lon: Double)
}