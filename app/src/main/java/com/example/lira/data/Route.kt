package com.example.lira.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route_table")
data class Route(

    @PrimaryKey(autoGenerate = true)
    var ident: Int,

    var id: Int,

    var name: String,

    var type: String
)