package com.example.ttcs_user.processBag.modelOrder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BagDao {
    @Query("SELECT * FROM Bag ")
    fun getAll(): LiveData<MutableList<Bag>>

    @Query("SELECT * FROM Bag WHERE Name LIKE :nameProduct  ")
    fun findByName(nameProduct: String): Bag

    @Insert
    fun insertAll(vararg product: Bag)

    @Delete
    fun delete(product: Bag)
    @Update
    fun updateUsers(vararg users: Bag)
    @Query("DELETE FROM Bag")
    fun deleteAll()
}