package com.example.rockpaperscissors

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "round_table")
data class Round(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "result")
    var result: Int,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "compchoice")
    var compchoice: String,

    @ColumnInfo(name = "yourchoice")
    var yourchoice: String

) : Parcelable