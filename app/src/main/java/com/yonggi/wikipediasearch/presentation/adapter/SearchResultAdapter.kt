package com.yonggi.wikipediasearch.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yonggi.wikipediasearch.R
import com.yonggi.wikipediasearch.databinding.ItemMediaBinding
import com.yonggi.wikipediasearch.domain.entity.Media
import com.yonggi.wikipediasearch.domain.usecase.GetBitmapUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class SearchResultAdapter(
    private val getBitmapUseCase: GetBitmapUseCase)
    : BaseAdapter() {

    private var list: List<Media> = emptyList()

    fun setList(list :List<Media>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMediaBinding.inflate(inflater, parent, false)
            view = binding.root

            holder = ViewHolder(binding)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = list[position]
        holder.bind(item, getBitmapUseCase)

        return view
    }


    private class ViewHolder(
        private val item: ItemMediaBinding) {

        fun bind(media: Media, useCase: GetBitmapUseCase) {
            item.media = media

            item.mediaImg.setImageResource(R.drawable.thumb_nail) // 기본 이미지

            // 비동기 이미지 불러오기
            GlobalScope.launch(Dispatchers.Main) {
                media.imgUrl.let { url ->
                    try {
                        val bitmap = useCase.execute(URL("https:$url"))
                        if (bitmap != null) {
                            item.mediaImg.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        // 예외 처리
                        Log.e("TEST","췤 $url")
                    }
                }
            }
        }
    }
}