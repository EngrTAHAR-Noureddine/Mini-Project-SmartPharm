package com.example.smartpharm.database.orders

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smartpharm.model.Orders


@Dao
interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Orders)

    @Query("SELECT * FROM orders_table ORDER BY orderId ASC")
    fun getAllOrders(): LiveData<List<Orders>>

    @Query("DELETE FROM orders_table")
    fun clear()

    @Update
    fun updateOrder(order: Orders)
}