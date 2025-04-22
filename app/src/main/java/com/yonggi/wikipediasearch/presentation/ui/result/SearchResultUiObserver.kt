package com.yonggi.wikipediasearch.presentation.ui.result

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.yonggi.wikipediasearch.databinding.ActivitySearchResultBinding
import com.yonggi.wikipediasearch.databinding.ViewHeaderBinding
import com.yonggi.wikipediasearch.domain.entity.MediaList
import com.yonggi.wikipediasearch.presentation.adapter.SearchResultAdapter
import com.yonggi.wikipediasearch.presentation.event.UiEffect
import com.yonggi.wikipediasearch.presentation.state.UiState

class SearchResultUiObserver(
    private val owner: LifecycleOwner,
    private val binding: ActivitySearchResultBinding,
    private val header: ViewHeaderBinding,
    private val adapter: SearchResultAdapter,
    private val model: SearchResultViewModel
) {

    fun observeAll() {
        observeUiState()
        observeUiEffect()
        observeSummary()
        observeMediaList()
        observeSummaryImage()
    }

    private fun observeUiState() {
        model.uiState.observe(owner) { state ->
            binding.swipeLayout.isRefreshing = false

            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    bindMediaList(state.list)
                }
                is UiState.Empty -> {
                    hideLoading()
                    showEmptyView(binding.root.context.getString(state.resId))
                }
                is UiState.Error -> {
                    hideLoading()
                    showError(binding.root.context.getString(state.resId))
                }
            }
        }
    }

    private fun observeUiEffect() {
        model.uiEffect.observe(owner) { effect ->
            when (effect) {
                is UiEffect.ShowErrorMessage -> {
                    val msg = binding.root.context.getString(effect.resId)
                    Toast.makeText(binding.root.context, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSummary() {
        model.summary.observe(owner) { data ->
            header.summary = data
            val layoutParams = header.summaryImg.layoutParams
            layoutParams.width = data.width
            layoutParams.height = data.height
            header.summaryImg.layoutParams = layoutParams
            model.getBitmap(data.imgUrl)
            binding.resultView.visibility = View.VISIBLE
        }
    }

    private fun observeMediaList() {
        model.mediaList.observe(owner) {
            binding.swipeLayout.isRefreshing = false
            adapter.setList(it.items)
        }
    }

    private fun observeSummaryImage() {
        model.sum_img.observe(owner) {
            header.summaryImg.setImageBitmap(it)
        }
    }

    private fun showLoading() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progress.visibility = View.GONE
    }

    private fun bindMediaList(list: MediaList) {
        binding.resultView.visibility = View.VISIBLE
        binding.emptyMsg.visibility = View.GONE
        adapter.setList(list.items)
    }

    private fun showEmptyView(message: String) {
        binding.resultView.visibility = View.GONE
        binding.emptyMsg.text = message
        binding.emptyMsg.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
    }
}
