package com.cms.android.cleaningmanagementsystem.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.data.*
import com.cms.android.cleaningmanagementsystem.app.databinding.RvTaskBinding
import com.cms.android.cleaningmanagementsystem.app.others.Constants


class TaskAdapter  : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>()  {

    class TaskViewHolder(val binding : RvTaskBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((TaskData) -> Unit)? = null

    fun setOnItemClickListener(position: (TaskData) -> Unit) {
        onItemClickListener = position
    }

    private var onItemClickListenerForCleanData: ((CleaningData) -> Unit)? = null

    fun setOnItemClickListenerForCleanData(position: (CleaningData) -> Unit) {
        onItemClickListenerForCleanData = position
    }


    private val diffCallback = object : DiffUtil.ItemCallback<TaskData>() {

        override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var taskList : List<TaskData>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = RvTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  TaskViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return taskList.size
    }


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = taskList[position]
        holder.itemView.apply {

            with(holder) {

                binding.tvTaskname.text = data.name
                if (data.id == "1"){
                    setShiftData2(data,context, R.color.main_color,R.color.green)
                }else{
                    setShiftData(data,context, R.color.main_color,R.color.green)
                }


            }


        /*    setOnClickListener {
                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }*/
        }
    }

    private fun TaskViewHolder.setShiftData(
        data: TaskData,context : Context,color : Int,green : Int
    ) {
        if (data.frequency >= 1) {
            binding.tv1.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 1 }
            binding.tv1.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv1.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv1.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv1.setOnClickListener {
                Constants.curTaskData = data
                if (binding.tv1.text != "Nil"){
                    onItemClickListenerForCleanData?.let {
                            click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 2) {
            binding.tv2.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 2 }
                binding.tv2.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv2.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv2.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv2.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv2.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 3) {
            binding.tv3.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 3 }
            binding.tv3.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv3.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv3.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv3.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv3.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 4) {
            binding.tv4.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 4 }
            binding.tv4.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv4.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv4.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv4.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv4.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 5) {
            binding.tv5.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 5 }
            binding.tv5.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv5.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv5.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv5.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv5.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 6) {
            binding.tv6.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 6 }
            binding.tv6.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv6.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv6.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv6.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv6.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 7) {
            binding.tv7.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 7 }
            binding.tv7.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv7.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv7.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv7.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv7.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 8) {
            binding.tv8.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 8 }
            binding.tv8.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv8.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv8.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv8.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv8.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 9) {
            binding.tv9.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 9 }
            binding.tv9.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv9.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv9.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv9.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv9.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 10) {
            binding.tv10.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 10 }
            binding.tv10.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv10.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv10.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv10.setOnClickListener {
                 Constants.curTaskData = data
                if (binding.tv10.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
    }

    private fun TaskViewHolder.setShiftData2(
        data: TaskData,context : Context,color : Int,green : Int
    ) {
        if (data.frequency >= 1) {
            binding.tv1.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 1 }
            binding.tv1.text = cleanData[0].rating.toString()
            when(cleanData[0].shift){
                1 -> binding.tv1.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv1.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv1.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv1.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv1.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv1.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let {
                            click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv1.visibility = View.GONE
        }
        if (data.frequency >= 2) {
            binding.tv2.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 2 }
            if (cleanData[0].isNil == YES){
                binding.tv2.text = "Nil"
            }else{
                binding.tv2.text = cleanData[0].rating.toString()
            }
            when(cleanData[0].shift){
                1 -> binding.tv2.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv2.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv2.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv2.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv2.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv2.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv2.visibility = View.GONE
        }
        if (data.frequency >= 3) {
            binding.tv3.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 3 }
            if (cleanData[0].isNil == YES){
                binding.tv3.text = "Nil"
            }else{
                binding.tv3.text = cleanData[0].rating.toString()
            }
            when(cleanData[0].shift){
                1 -> binding.tv3.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv3.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv3.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv3.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv3.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv3.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv3.visibility = View.GONE
        }
        if (data.frequency >= 4) {
            binding.tv4.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 4 }
            if (cleanData[0].isNil == YES){
                binding.tv4.text = "Nil"
            }else{
                binding.tv4.text = cleanData[0].rating.toString()
            }
            when(cleanData[0].shift){
                1 -> binding.tv4.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv4.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv4.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv4.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv4.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv4.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv4.visibility = View.GONE
        }
        if (data.frequency >= 5) {
            binding.tv5.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 5 }
            if (cleanData[0].isNil == YES){
                binding.tv5.text = "Nil"
            }else{
                binding.tv5.text = cleanData[0].rating.toString()
            }
            when(cleanData[0].shift){
                1 -> binding.tv5.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv5.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv5.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv5.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv5.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv5.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv5.visibility = View.GONE
        }
        if (data.frequency >= 6) {
            binding.tv6.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 6 }
            when(cleanData[0].shift){
                1 -> binding.tv6.setBackgroundColor(ContextCompat.getColor(context,R.color.shift1))
                2 -> binding.tv6.setBackgroundColor(ContextCompat.getColor(context,R.color.shift2))
                3 -> binding.tv6.setBackgroundColor(ContextCompat.getColor(context,R.color.shift3))
            }
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv6.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv6.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            if (cleanData[0].isNil == YES){
                binding.tv6.text = "Nil"
            }else{
                binding.tv6.text = cleanData[0].rating.toString()
            }
            binding.tv6.setOnClickListener {
                Constants.curTaskData = data
                if (cleanData[0].isNil == NO){
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }else{
            binding.tv6.visibility = View.GONE
        }
        if (data.frequency >= 7) {
            binding.tv7.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 7 }
            binding.tv7.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv7.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv7.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv7.setOnClickListener {
                Constants.curTaskData = data
                if (binding.tv7.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 8) {
            binding.tv8.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 8 }
            binding.tv8.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv8.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv8.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv8.setOnClickListener {
                Constants.curTaskData = data
                if (binding.tv8.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 9) {
            binding.tv9.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 9 }
            binding.tv9.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv9.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv9.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv9.setOnClickListener {
                Constants.curTaskData = data
                if (binding.tv9.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
        if (data.frequency >= 10) {
            binding.tv10.visibility = View.VISIBLE
            val cleanData = data.CleaningData.filter { it.occurrence == 10 }
            binding.tv10.text = cleanData[0].rating.toString()
            if (cleanData[0].status != FILL) {
                val rating = cleanData[0].rating
                if (rating>3){
                    binding.tv10.setTextColor(ContextCompat.getColor(context,green))
                }else{
                    binding.tv10.setTextColor(ContextCompat.getColor(context,color))
                }
            }
            binding.tv10.setOnClickListener {
                Constants.curTaskData = data
                if (binding.tv10.text != "Nil") {
                    onItemClickListenerForCleanData?.let { click ->
                        click(cleanData[0])
                    }
                }
            }
        }
    }

}