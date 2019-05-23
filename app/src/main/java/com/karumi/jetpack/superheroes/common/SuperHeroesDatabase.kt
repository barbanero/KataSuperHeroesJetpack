package com.karumi.jetpack.superheroes.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karumi.jetpack.superheroes.data.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.room.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class], version = 1)
abstract class SuperHeroesDatabase : RoomDatabase() {
  abstract fun superHeroesDao(): SuperHeroDao
}