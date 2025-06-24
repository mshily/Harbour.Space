package com.vina_esima.final_project.analytics.EnrtyDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "calender")
class CalendarModel (
    @PrimaryKey (autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "day")
    val day: Int,

    @ColumnInfo(name = "entry")
    val entry: EntryModel,
)
