package com.achsanit.newsnippet.di

import com.achsanit.newsnippet.ui.fragment.bookmark.BookmarkViewModel
import com.achsanit.newsnippet.ui.fragment.detail.DetailViewModel
import com.achsanit.newsnippet.ui.fragment.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { BookmarkViewModel() }
    viewModel { DetailViewModel() }
}