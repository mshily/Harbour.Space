package com.vina_esima.final_project.analytics.EnrtyDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vina_esima.final_project.analytics.dateInDay

@Entity (tableName = "entry")
class EntryModel (
    @PrimaryKey (autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "start")
    val start: dateInDay,

    @ColumnInfo(name = "end")
    val end: dateInDay,

    @ColumnInfo(name = "activity")
    val activity: String,
)