package com.vina_esima.final_project.ActivityButtonDB.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "activity_buttons")
class ActivityButtonsModel (
    @PrimaryKey (autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

)