package com.cms.android.cleaningmanagementsystem.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cms.android.cleaningmanagementsystem.app.data.StationData
import com.cms.android.cleaningmanagementsystem.app.databinding.RvUserBinding


class UserAdapter()  : RecyclerView.Adapter<UserAdapter.UserViewHolder>()  {

    class UserViewHolder(val binding : RvUserBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((StationData) -> Unit)? = null

    fun setOnItemClickListener(position: (StationData) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<StationData>() {

        override fun areContentsTheSame(oldItem: StationData, newItem: StationData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: StationData, newItem: StationData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var featureList : List<StationData>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = RvUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  UserViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return featureList.size
    }


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = featureList[position]
        holder.itemView.apply {

            with(holder) {

                binding.tvName.text = data.name
                binding.tvRole.text = data.userType

            }

            setOnClickListener {
                onItemClickListener?.let {
                        click ->
                    click(data)
                }
                notifyDataSetChanged()
            }
        }
    }





}
