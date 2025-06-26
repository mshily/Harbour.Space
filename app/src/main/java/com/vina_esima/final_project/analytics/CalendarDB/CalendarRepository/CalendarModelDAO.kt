package com.vina_esima.final_project.analytics.CalendarDB.CalendarRepository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vina_esima.final_project.analytics.EnrtyDB.CalendarModel

@Dao
interface CalendarModelDAO {
    @Query("SELECT * FROM calender")
    fun getAll(): LiveData<List<CalendarModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insert(day: CalendarModel)

    @Query("DELETE FROM calender")
    fun deleteAll()

    @Query("DELETE FROM calender where id = :id")
    fun deleteById(id: String)

}
