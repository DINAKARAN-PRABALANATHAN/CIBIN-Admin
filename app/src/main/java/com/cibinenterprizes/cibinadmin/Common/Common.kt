package com.cibinenterprizes.cibinadmin.Common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.cibinenterprizes.cibinadmin.Model.Token
import com.cibinenterprizes.cibinadmin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Common {

    var user = FirebaseAuth.getInstance().currentUser
    var database = FirebaseDatabase.getInstance().getReference()
    val idAuth = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun updateToken(context: Context, token: String, district: String) {
        FirebaseDatabase.getInstance().getReference().child("AdminToken").child(district).setValue(token).addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
    fun showNotification(context: Context, id: Int, title: String?, content: String?, intent: Intent?) {

        val NOTIFICATION_CHANNEL_ID = "com.cibinenterprizes.cibinadmin"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,"Cibin Notification",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "Cibin Notification"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = (Color.GREEN)

            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle(title).setContentText(content).setAutoCancel(true)
            .setSmallIcon(R.drawable.logo)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content).setBigContentTitle(title).setSummaryText("Work Update"))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))


        val notification = builder.build()
        notificationManager.notify(id,notification)
    }
}