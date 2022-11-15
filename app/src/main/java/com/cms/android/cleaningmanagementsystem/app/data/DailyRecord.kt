package com.cms.android.cleaningmanagementsystem.app.data

data class DailyRecordData(
    var id : String = "",
    var date : String = "",
    var tasksList : List<TaskData> = listOf(),
    var stationName : String = "",
    var stationId : String = ""
)