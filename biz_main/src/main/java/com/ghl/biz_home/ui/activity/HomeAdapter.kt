package com.ghl.biz_home.ui.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ghl.biz_home.R
import com.ghl.biz_home.bean.ArticleListDatas
import com.ghl.biz_home.databinding.AdapterHomeLayoutBinding
import com.ghl.common.extension.d2p

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var itemClick: ((String) -> Unit)? = null
    private val mList = mutableListOf<ArticleListDatas>()

    fun notify(list: MutableList<ArticleListDatas>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            AdapterHomeLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(position, mList.size)
    }

    override fun getItemCount(): Int = mList.size


    inner class HomeViewHolder(private val binding: AdapterHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        private val tvSub: TextView = itemView.findViewById(R.id.tv_sub)
        private var mContext = itemView.context

        @SuppressLint("SetTextI18n")
        fun bind(position: Int, size: Int) {
            val bean = mList[position]
            val color = ContextCompat.getColor(mContext, R.color.xz_ffffff)
            val radius = 15f.d2p(mContext)

            tvName.run {
                text = bean.author
            }

            tvTime.run {
                text = "-${bean.niceShareDate}"
            }

            tvContent.run {
                text = bean.title
            }

            tvSub.run {
                text = "${bean.superChapterName}-${bean.chapterName}"
            }

            binding.root.setOnClickListener {
                itemClick?.invoke(bean.link)
            }
        }
    }
}

