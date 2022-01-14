package com.cibinenterprizes.cibinadmin.Services

import com.cibinenterprizes.cibinadmin.Common.Common
import com.cibinenterprizes.cibinadmin.Common.Common.database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class MyFCMServices: FirebaseMessagingService() {

    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onNewToken(pO: String) {
        super.onNewToken(pO)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(Common.idAuth).child("Profile").child("district").getValue().toString()
                Common.updateToken(this@MyFCMServices, pO, district)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val dataTitle = remoteMessage.data.get("title")
        val dataContent = remoteMessage.data.get("content")
        Common.showNotification(this, Random().nextInt(), dataTitle, dataContent, null)
    }




}