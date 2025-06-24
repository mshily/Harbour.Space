package com.vina_esima.final_project.ActivityButtonDB.model.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vina_esima.final_project.ActivityButtonDB.model.ActivityButtonsModel

@Dao
interface ActivityButtonsModelDao {
    @Query("SELECT * FROM activity_buttons")
    fun getAll(): LiveData<List<ActivityButtonsModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insert(activity: ActivityButtonsModel)

    @Query("SELECT MAX(id) FROM activity_buttons")
    fun getMaxId(): Int?


    @Query("DELETE FROM activity_buttons")
    fun deleteAll()

    @Query("DELETE FROM activity_buttons where name = :name")
    fun deleteByName(name: String)

}
