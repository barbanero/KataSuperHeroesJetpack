package com.karumi.jetpack.superheroes.common

import com.karumi.jetpack.superheroes.data.room.SuperHeroEntity
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SuperHeroMapper {

  fun mapEntitytoModel(entities: List<SuperHeroEntity>): List<SuperHero> {
  return entities.map { mapEntitytoModel(it) }
  }

  fun mapEntitytoModel(entity: SuperHeroEntity): SuperHero {
    return SuperHero(
        entity.id,
        entity.name,
        entity.photo,
        entity.isAvenger,
        entity.description)
  }
}