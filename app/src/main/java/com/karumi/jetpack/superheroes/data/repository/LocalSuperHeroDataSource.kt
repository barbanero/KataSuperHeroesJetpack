package com.karumi.jetpack.superheroes.data.repository

import com.karumi.jetpack.superheroes.common.SuperHeroMapper
import com.karumi.jetpack.superheroes.data.room.SuperHeroDao
import com.karumi.jetpack.superheroes.data.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import java.util.concurrent.ExecutorService

class LocalSuperHeroDataSource(
    private val executor: ExecutorService,
    private val superHeroDao: SuperHeroDao,
    private val mapper: SuperHeroMapper
) {
  companion object {
    private const val BIT_TIME = 250L
  }

  private val superHeroes: MutableMap<String, SuperHero> = mutableMapOf()

  fun getAllSuperHeroes(): List<SuperHero> {
    waitABit()
    return superHeroDao.getAll().let { mapper.mapEntitytoModel(it) }
  }

  fun get(id: String): SuperHero? {
    waitABit()
    return superHeroDao.getById(id)?.let { mapper.mapEntitytoModel(it) }
  }

  fun saveAll(all: List<SuperHero>) = executor.execute {
    waitABit()
    superHeroes.clear()
    superHeroes.putAll(all.associateBy { it.id })
  }

  fun save(superHero: SuperHero): SuperHero {
    executor.execute {
      waitABit()
      superHeroes[superHero.id] = superHero
    }
    return superHero
  }

  private fun waitABit() {
    Thread.sleep(BIT_TIME)
  }

  private fun SuperHeroEntity.toSuperHero(): SuperHero =
      SuperHero(id, name, photo, isAvenger, description)

  private fun SuperHero.toEntity(): SuperHeroEntity =
      SuperHeroEntity(id, name, photo, isAvenger, description)
}