package com.cms.android.cleaningmanagementsystem.app.data

data class TaskData(
    var id : String = "",
    var name : String = "",
    var frequency : Int = 1,
    var CleaningData : List<CleaningData> = listOf(),
    var nilList : List<Int> = listOf()
)
data class CleaningData(
    var shift : Int = 0,
    var rating : Int = 0,
    var comment : String = "",
    var status : String = FILL,
    var img : String = "",
    var img2 : String = "",
    var img3 : String = "",
    var img4 : String = "",
    var slot : String = "",
    var occurrence : Int = 0,
    var isNil : String = NO
)

const val FILL = "Fill"
const val VIEW = "View"