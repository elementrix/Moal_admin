package com.e.moal_admin

//이 코틀린 클래스는 파이어 베이스에서 읽어온 정보를 보충하여 유저에게 보여주기 위한 클래스 입니다.

class JobTimeForReading (
    var startHour : Int,
    var startMin : Int,
    var endHour : Int,
    var endMin : Int,
    var requirePeopleNum : Int,
    val storeName: String,
    val positionName : String,
    val partName : String,
    val jobDay: String
)