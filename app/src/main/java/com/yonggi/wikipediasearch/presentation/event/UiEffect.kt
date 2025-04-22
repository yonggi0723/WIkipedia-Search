package com.yonggi.wikipediasearch.presentation.event

sealed class UiEffect {

    data class ShowErrorMessage(val resId: Int) : UiEffect()
}