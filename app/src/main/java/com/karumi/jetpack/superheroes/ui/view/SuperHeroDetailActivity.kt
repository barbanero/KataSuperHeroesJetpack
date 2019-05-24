package com.karumi.jetpack.superheroes.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer

import com.karumi.jetpack.superheroes.R
import com.karumi.jetpack.superheroes.common.bindViewModel
import com.karumi.jetpack.superheroes.common.module
import com.karumi.jetpack.superheroes.databinding.SuperHeroDetailActivityBinding
import com.karumi.jetpack.superheroes.domain.usecase.GetSuperHeroById
import com.karumi.jetpack.superheroes.ui.presenter.SuperHeroDetailPresenter
import kotlinx.android.synthetic.main.super_hero_detail_activity.toolbar
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

class SuperHeroDetailActivity :
    BaseActivity2<SuperHeroDetailActivityBinding>() {
  companion object {
    private const val SUPER_HERO_ID_KEY = "super_hero_id_key"

    fun open(activity: Activity, superHeroId: String) {
      val intent = Intent(activity, SuperHeroDetailActivity::class.java)
      intent.putExtra(SUPER_HERO_ID_KEY, superHeroId)
      activity.startActivity(intent)
    }
  }

  private val model: SuperHeroDetailPresenter by viewModel()
  override val layoutId: Int = R.layout.super_hero_detail_activity
  override val toolbarView: Toolbar
    get() = toolbar
  private val superHeroId: String by lazy { intent?.extras?.getString(SUPER_HERO_ID_KEY) ?: "" }

  override fun configureBinding(binding: SuperHeroDetailActivityBinding) {
    binding.viewModel = model
  }

  override fun prepare(intent: Intent?) {
    title = superHeroId
    model.prepare(superHeroId)
    model.superHero.observe(this, Observer { title = it?.name })
    model.idOfSuperHeroToEdit.observe(
        this,
        Observer { it?.run { openEditSuperHero(this) } })
  }

  private fun openEditSuperHero(superHeroId: String) = runOnUiThread {
    EditSuperHeroActivity.open(this, superHeroId)
  }

  override val activityModules = module {
    bind<SuperHeroDetailPresenter>() with provider {
      SuperHeroDetailPresenter(instance())
    }
    bind<GetSuperHeroById>() with provider { GetSuperHeroById(instance()) }
    bindViewModel<SuperHeroDetailPresenter>() with provider {
      SuperHeroDetailPresenter(instance())
    }
  }
}