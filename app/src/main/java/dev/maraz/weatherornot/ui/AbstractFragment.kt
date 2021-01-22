package dev.maraz.weatherornot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

abstract class AbstractFragment<VM : ViewModel>(viewModelClass: KClass<VM>) : Fragment() {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore }, null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(getViewResource(), container, false)
    }

    @LayoutRes
    protected abstract fun getViewResource(): Int

    protected fun <T> LiveData<T>.observe(block: (T) -> Unit) = observe(viewLifecycleOwner, block)

}