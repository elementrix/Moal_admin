package com.e.moal_admin

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var usernname: String? = "",
    var email: String? =""
)