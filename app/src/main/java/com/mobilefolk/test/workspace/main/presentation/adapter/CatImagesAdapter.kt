package com.mobilefolk.test.workspace.main.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.mobilefolk.test.databinding.*
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.domain.models.ImagePlaceholder
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class CatImagesAdapter(private val listener: OnNewsListener) :
    ListAdapter<CatImage, CatImagesAdapter.BaseViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return when (getItem(viewType).placeholder) {
            ImagePlaceholder.Loading -> {
                val shimmerBinding =
                    ItemNewsShimmerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                NewsShimmerViewHolder(shimmerBinding)
            }

            is ImagePlaceholder.Error -> {
                val errorBinding =
                    ItemNewsRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewsErrorViewHolder(errorBinding)
            }
            else -> {
                val binding =
                    ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewsViewHolder(binding, parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    abstract inner class BaseViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: CatImage, position: Int)
        fun getShimmerDrawable(): ShimmerDrawable {
            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            return ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
        }

        fun parseDateTime(dateTime: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            format.timeZone = TimeZone.getTimeZone("UTC")
            val prettyTime = PrettyTime(Locale.ENGLISH)
            return prettyTime.format(format.parse(dateTime))
        }
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding, private val context: Context) :
        BaseViewHolder(binding) {
        init {
            binding.root.setOnClickListener {
                listener.onNewsClick(getItem(bindingAdapterPosition))
            }
        }

        override fun bind(item: CatImage, position: Int) {

        }
    }

    inner class NewsShimmerViewHolder(
        binding: ItemNewsShimmerBinding
    ) :
        BaseViewHolder(binding) {
        override fun bind(item: CatImage, position: Int) {

        }

    }

    inner class NewsErrorViewHolder(
        private val binding: ItemNewsRetryBinding
    ) :
        BaseViewHolder(binding) {
        init {
            binding.retryBtn.setOnClickListener {
                listener.onRetryClick()
            }
        }

        override fun bind(item: CatImage, position: Int) {
            binding.apply {
                error.text = (item.placeholder as ImagePlaceholder.Error).error
            }
        }

    }

    interface OnNewsListener {
        fun onNewsClick(item: CatImage)
        fun onRetryClick()
    }

    class DiffCallback : DiffUtil.ItemCallback<CatImage>() {
        override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage) =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage) =
            oldItem == newItem
    }
}