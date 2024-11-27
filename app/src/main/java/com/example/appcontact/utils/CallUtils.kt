package com.example.appcontact.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 * Mở ứng dụng Dialer với số điện thoại được truyền vào
 * @param context: Context của ứng dụng
 * @param phone: Số điện thoại cần gọi
 */
fun openDialer(context: Context, phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
    }
}

/**
 * Thực hiện cuộc gọi trực tiếp (Yêu cầu quyền CALL_PHONE)
 * @param context: Context của ứng dụng
 * @param phone: Số điện thoại cần gọi
 */
fun makeCall(context: Context, phone: String) {
    try {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to make call", Toast.LENGTH_SHORT).show()
    }
}

