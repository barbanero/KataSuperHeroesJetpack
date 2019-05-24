package com.karumi.jetpack.superheroes.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.kodein.di.erased.bind
import androidx.lifecycle.ViewModelProvider
import com.karumi.jetpack.superheroes.common.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

abstract class BaseActivity2<T : ViewDataBinding> : AppCompatActivity(), KodeinAware {

    private val appKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(appKodein)
        import(activityModules)
    }

    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    abstract val activityModules: Kodein.Module
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        configureBinding(binding)
        setSupportActionBar(toolbarView)
        prepare(intent)
    }

    private fun Kodein.MainBuilder.includeViewModelFactory() {
        bind<ViewModelProvider.Factory>() with singleton {
            ViewModelFactory(instance(), instance())
        }
    }

    abstract fun configureBinding(binding: T)
    open fun prepare(intent: Intent?) {}
}