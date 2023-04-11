package com.mobilefolk.test.workspace.main.presentation.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mobilefolk.test.R
import com.mobilefolk.test.core.utils.getShimmerDrawable
import com.mobilefolk.test.databinding.ItemCatImageBinding
import com.mobilefolk.test.databinding.ItemCatImageRetryBinding
import com.mobilefolk.test.databinding.ItemCatImageShimmerBinding
import com.mobilefolk.test.workspace.main.domain.models.CatImage
import com.mobilefolk.test.workspace.main.domain.models.ImagePlaceholder
import timber.log.Timber

class CatImagesAdapter(private val listener: OnImageListener) :
  ListAdapter<CatImage, CatImagesAdapter.BaseViewHolder>(DiffCallback()) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): BaseViewHolder {
    return when (getItem(viewType).placeholder) {
      ImagePlaceholder.Loading -> {
        val shimmerBinding =
          ItemCatImageShimmerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
          )
        CatImageShimmerViewHolder(shimmerBinding)
      }
      
      is ImagePlaceholder.Error -> {
        val errorBinding =
          ItemCatImageRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        CatImageErrorViewHolder(errorBinding)
      }
      else -> {
        val binding =
          ItemCatImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        CatImageViewHolder(binding, parent.context)
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
  
  override fun onViewRecycled(holder: BaseViewHolder) {
    if (holder is CatImageViewHolder) {
      Glide.with(holder.itemView).clear(holder.imageView);
    }
    super.onViewRecycled(holder)
  }
  
  abstract inner class BaseViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: CatImage, position: Int)
    
  }
  
  inner class CatImageViewHolder(
    private val binding: ItemCatImageBinding,
    private val context: Context
  ) :
    BaseViewHolder(binding) {
    var imageView: ImageView
    
    init {
      binding.apply {
        imageView = thumbnailBottom
        root.setOnClickListener {
          listener.onTap(getItem(bindingAdapterPosition))
        }
        retryBtn.setOnClickListener {
          loadImage(getItem(bindingAdapterPosition))
        }
      }
      
    }
    
    override fun bind(item: CatImage, position: Int) {
      loadImage(item)
    }
    
    private fun loadImage(item: CatImage) {
      Glide.with(context)
        .load(item.url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .placeholder(context.getShimmerDrawable())
        .listener(object : RequestListener<Drawable> {
          override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
          ): Boolean {
            binding.retryBtn.visibility = View.VISIBLE
            return false;
          }
          
          override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
          ): Boolean {
            binding.retryBtn.visibility = View.GONE
            return false
          }
        })
        .error(ColorDrawable(ContextCompat.getColor(context, R.color.colorShimmer)))
        .centerCrop()
        .into(binding.thumbnailBottom)
    }
  }
  
  inner class CatImageShimmerViewHolder(
    binding: ItemCatImageShimmerBinding
  ) :
    BaseViewHolder(binding) {
    override fun bind(item: CatImage, position: Int) {
    
    }
  }
  
  inner class CatImageErrorViewHolder(
    private val binding: ItemCatImageRetryBinding
  ) :
    BaseViewHolder(binding) {
    init {
      binding.retryBtn.setOnClickListener {
        listener.onRetry()
      }
    }
    
    override fun bind(item: CatImage, position: Int) {
      binding.apply {
        error.text = (item.placeholder as ImagePlaceholder.Error).error
      }
    }
  }
  
  interface OnImageListener {
    fun onTap(item: CatImage)
    fun onRetry()
  }
  
  class DiffCallback : DiffUtil.ItemCallback<CatImage>() {
    override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage) =
      oldItem == newItem
    
    override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage) =
      oldItem == newItem
  }
}