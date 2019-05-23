package com.karumi.jetpack.superheroes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.karumi.jetpack.superheroes.domain.model.SuperHero

class SuperHeroRepository(
    private val local: LocalSuperHeroDataSource,
    private val remote: RemoteSuperHeroDataSource
) {
  fun getAllSuperHeroes(): LiveData<List<SuperHero>> {
    val liveData = MediatorLiveData<List<SuperHero>>()
    val localSuperHero: LiveData<List<SuperHero>> = local.getAllSuperHeroes()
    liveData.addSource(localSuperHero) { it ->
      if (it.isNotEmpty()) {
        liveData.postValue(it)
      } else {
        liveData.addSource(remote.getAllSuperHeroes()) {
          it?.apply { local.saveAll(this) }
        }
      }
    }
    return liveData
  }

  fun get(id: String): LiveData<SuperHero?> {
    val liveData = MediatorLiveData<SuperHero?>()
    val localSuperHero: LiveData<SuperHero?> = local.get(id)
    liveData.addSource(localSuperHero) { it ->
      if (it != null) {
        liveData.postValue(it)
      } else {
        liveData.addSource(remote.getAllSuperHeroes()) {
          it?.apply { local.saveAll(this) }
        }
      }
    }
    return liveData
  }

  fun save(superHero: SuperHero): SuperHero {
    local.save(superHero)
    remote.save(superHero)
    return superHero
  }
}