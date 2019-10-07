package com.e.moal_admin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class StoreInfo(
    var Address: String? = "",
    var name: String? =""
)