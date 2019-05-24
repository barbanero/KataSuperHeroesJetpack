package com.karumi.jetpack.superheroes.ui.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.karumi.jetpack.superheroes.domain.model.SuperHero
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.view.SingleLiveEvent

class SuperHeroDetailPresenter(
    private val getSuperHeroById: GetSuperHeroById
) : ViewModel(), SuperHeroDetailListener, LifecycleObserver {

  val isLoading = MutableLiveData<Boolean>()
  val superHero = MediatorLiveData<SuperHero?>()
  val idOfSuperHeroToEdit = SingleLiveEvent<String>()

  private lateinit var id: String

  fun prepare(id: String) {
    isLoading.value = true
    superHero.addSource(getSuperHeroById(id)) {
      superHero.postValue(it)
      isLoading.value = false
    }
  }

  override fun onEditSelected() {
    idOfSuperHeroToEdit.value = id
  }
}

interface SuperHeroDetailListener {
  fun onEditSelected()
}