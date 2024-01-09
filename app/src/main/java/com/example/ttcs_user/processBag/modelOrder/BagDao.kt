package com.example.ttcs_user.processBag.modelOrder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BagDao {
    @Query("SELECT * FROM Bag ")
    fun getAll(): LiveData<MutableList<Bag>>

    @Query("SELECT * FROM Bag WHERE Name LIKE :Name_Product  " )
    fun findByName(Name_Product: String): Bag

    @Insert
    fun insertAll(vararg Product: Bag)

    @Delete
    fun delete(Product: Bag)
    @Update
    fun updateUsers(vararg users: Bag)
    @Delete
    fun DeleteAll(all: MutableList<Bag>)
}