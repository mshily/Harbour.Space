package com.vina_esima.final_project.analytics.EnrtyDB.EntryRepository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vina_esima.final_project.analytics.EnrtyDB.EntryModel

@Dao
interface EntryModelDAO {
    @Query("SELECT * FROM entry")
    fun getAll(): LiveData<List<EntryModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insert(entry: EntryModel)

    @Query("DELETE FROM entry")
    fun deleteAll()

    @Query("DELETE FROM entry where id = :id")
    fun deleteById(id: String)
}
