package it.branjsmo.memorygame.di

import it.branjsmo.memorygame.presentation.screen.MemoryGameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    viewModel { MemoryGameViewModel() }

}