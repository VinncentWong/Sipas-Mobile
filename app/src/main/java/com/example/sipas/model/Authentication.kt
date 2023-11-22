package com.example.sipas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication")
class Authentication(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "jwt_token")
    val jwtToken: String
){}