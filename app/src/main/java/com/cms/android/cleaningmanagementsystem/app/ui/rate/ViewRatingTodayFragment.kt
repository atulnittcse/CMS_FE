package com.cms.android.cleaningmanagementsystem.app.ui.rate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.adapter.TaskAdapter
import com.cms.android.cleaningmanagementsystem.app.data.*
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentHomeBinding
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentLoginBinding
import com.cms.android.cleaningmanagementsystem.app.firebase.MainViewmodel
import com.cms.android.cleaningmanagementsystem.app.others.Constants
import com.cms.android.cleaningmanagementsystem.app.others.MyDialog
import com.cms.android.cleaningmanagementsystem.app.others.SharedPref
import com.google.android.material.datepicker.MaterialDatePicker
import okhttp3.internal.concurrent.Task
import java.text.SimpleDateFormat
import java.util.*

class ViewRatingTodayFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding : FragmentHomeBinding
    lateinit var taskAdapter : TaskAdapter
    lateinit var stationData : StationData
    lateinit var viewmodel : MainViewmodel
    lateinit var myDialog: MyDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        taskAdapter = TaskAdapter()
        viewmodel =
            ViewModelProvider(this).get(MainViewmodel::class.java)
        myDialog = MyDialog(requireContext())

        binding.tvDate.visibility = View.INVISIBLE
        binding.tvDay.visibility = View.INVISIBLE

        binding.rvList.adapter = taskAdapter
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())

        stationData = SharedPref(requireContext()).getUserData()

        taskAdapter.taskList = getTaskList()


        val calendar = Calendar.getInstance()

        val date = getDate(calendar.timeInMillis)

        binding.tvDay.text = dayOfWeek()
        binding.tvDate.text = getDate(Calendar.getInstance().timeInMillis)

        viewmodel.getDailyRecordByID(date)


        setCallbacks()


    }

    fun dayOfWeek() : String {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return (when(day){
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "Time has stopped"
        })
    }

    private fun setCallbacks() {

        viewmodel.dailyRecordLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null){
                binding.progressbar.visibility = View.GONE
                binding.rvList.visibility = View.VISIBLE
                Constants.curDailyRecordData = it
                taskAdapter.taskList = it.tasksList
            }else{
                val tempList = makeDummyData()
                binding.progressbar.visibility = View.GONE
                binding.rvList.visibility = View.VISIBLE
                Constants.curDailyRecordData = tempList
                taskAdapter.taskList = tempList.tasksList
            }
        })

        viewmodel.errorDailyRecordLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.progressbar.visibility  = View.GONE
            myDialog.showErrorAlertDialog(it)
        })

    }

    private fun makeDummyData() : DailyRecordData{


        val date = binding.tvDate.text.toString()

        val dailyRecordData =  DailyRecordData(date,date,getTaskList(),stationData.name,stationData.id)


        return dailyRecordData

    }


    fun getDate(ms : Long) : String{
        val format = SimpleDateFormat("dd, MMM yyyy")
        return format.format(ms)
    }


    private fun getTaskList(): List<TaskData> {
        val data1 = TaskData("1","All Circulating area (North, South)",6, getCleanDataList(6))
        val data2 = TaskData("2","All Circulating area (2nd entry south, Kachha road 2nd entry)",5,
            getCleanDataList(5))
        val data3 = TaskData("3","All Offices (Station building at North & South side,Railtel, North coolie room, south side coolie room)",
            4,     getCleanDataList(4))
        val data4 = TaskData("4","All PRS & Booking with Hall(North, South & 2nd entry South) and SBO 2 (2nd entry)",
            7,    getCleanDataList(7))
        val data5 = TaskData("5","All Platforms(1 to 10), Entrance, Portico, Dado & Granite benches around PF",8,
            getCleanDataList(8))
        val data6 = TaskData("6","All FOB (Ramp of East FOB, Surface area including steps of FOB and Office)",7,
            getCleanDataList(7))
        val data7 = TaskData("7","All washable tracks (Track No. 1, 4-11 & 13)",4,  getCleanDataList(4))
        val data8 = TaskData("8","All washable tracks (Track No. 1, 4-11 & 13) ",2,  getCleanDataList(2))
        val data9 = TaskData("9","All Non-washable tracks (Track No. 2, 3 & 12) Rag picking cum Hard broom Sweeping",3,
            getCleanDataList(3))
        val data10 = TaskData("10","Rag picking area( yard) from Kms 542/5 to 544/3 and PF-10 Gaya end to PG line L/C" ,2, getCleanDataList(2))
        val data11 = TaskData("11","All Pathway Area" ,1, getCleanDataList(1))
        val data12 = TaskData("12","All Drains (Adjacent track including circulating area North & South)" ,2, getCleanDataList(2))
        val data13 = TaskData("13","All Main Drains & Outfall drain " ,1, getCleanDataList(1))
        val data14 = TaskData("14","All Escalators " ,5, getCleanDataList(5))
        val data15 = TaskData("15","All Retiring Rooms (North & South side)" ,4, getCleanDataList(4))
        val data16 = TaskData("16","All area of Facade, Glass & Signage Board Cleaning" ,2, getCleanDataList(2))
        val data17 = TaskData("17","All Urinals, Toilets, washbasins of PF 1-10 & all\n" + "water booths cleaning" ,10, getCleanDataList(10))
        val data18 = TaskData("18","All Area of Steel and Iron Railing(All FOB)" ,6, getCleanDataList(6))
        val data19 = TaskData("19","Ceiling of all FOB, all offices, entrance and portico,\n" + "PRS and booking North & South & Roof area for\n" + "cobwebs and sweeping" ,1, getCleanDataList(1))
        val data20 = TaskData("20","All Dustbins cleaning (No-120)" ,6, getCleanDataList(6))
        val data21 = TaskData("21","Surface area of all well" ,2, getCleanDataList(2))
        val data22 = TaskData("22","Sump and Manhole" ,1, getCleanDataList(1))
        val data23 = TaskData("23","Total area of Well" ,1, getCleanDataList(1))
        val data24 = TaskData("24","Washing of all PF, PRS & Booking office & steps of\n" + "FOB & offices" ,1, getCleanDataList(1))
        val data25 = TaskData("25","All Septic tank" ,1, getCleanDataList(1))
        return listOf(data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13,data14,data15,data16,data17,data18,
        data19,data20,data21,data22,data23,data24,data25)
    }

    fun getCleanDataList(shift : Int) : List<CleaningData>{

        val cleanList = mutableListOf<CleaningData>()
        for (data in 1..shift){
            cleanList.add(CleaningData(data,0,"", FILL))
        }
        return cleanList

    }

}