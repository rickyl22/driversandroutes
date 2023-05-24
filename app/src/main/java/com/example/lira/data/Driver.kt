package com.example.lira.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver_table")
data class Driver(

    @PrimaryKey(autoGenerate = true)
    var ident: Int,

    var id: String,

    var name: String,

)