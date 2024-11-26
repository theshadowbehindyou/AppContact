package com.example.appcontact.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID tự động tăng
    val name: String, // Tên liên hệ
    val phone: String, // Số điện thoại
    val email: String, // Email
    val avatar: String // URI ảnh đại diện (dạng String)
)