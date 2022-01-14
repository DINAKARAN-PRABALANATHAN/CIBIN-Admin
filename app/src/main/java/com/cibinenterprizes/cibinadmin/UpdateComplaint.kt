package com.cibinenterprizes.cibinadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.cibinenterprizes.cibinadmin.Model.FCMResponse
import com.cibinenterprizes.cibinadmin.Model.FCMSendData
import com.cibinenterprizes.cibinadmin.Remote.IFCMService
import com.cibinenterprizes.cibinadmin.Remote.RetrofitFCMClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_update_complaint.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateComplaint : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var complaintIdForUpdate: String
    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var database = FirebaseDatabase.getInstance().reference
    lateinit var ifcmService: IFCMService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_complaint)

        ifcmService = RetrofitFCMClient.getInstance("https://fcm.googleapis.com/").create(IFCMService::class.java)

        val spinner1 = findViewById<Spinner>(R.id.complaints_status)
        val adapter1 = ArrayAdapter.createFromResource(this,R.array.status,android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.setAdapter(adapter1)
        spinner1.setOnItemSelectedListener(this)

        complaintIdForUpdate = getIntent().extras?.get("Complaint ID").toString()
        Toast.makeText(this,complaintIdForUpdate, Toast.LENGTH_SHORT).show()

        RetriveBinData()
        complaints_update_back_botton.setOnClickListener {
            finish()
        }
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                val userId = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("UserDetails").child("authId").getValue().toString()
                var userToken = snapshot.child("Tokens").child(userId).child("token").getValue().toString()
                complaints_update_botton.setOnClickListener{
                    updateBin(spinner1, userId, userToken, district)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
    private fun updateBin(spinner1: Spinner, userId: String, userToken: String, district: String) {

        val status = spinner1.getSelectedItem().toString()

        database.child("User Details").child(userId).child("Complaints").child(complaintIdForUpdate).child("status").setValue(status).addOnCompleteListener {
            database.child("Complaints").child(district).child(complaintIdForUpdate).child("status").setValue(status).addOnCompleteListener {
                val dataSend = HashMap<String, String>()
                dataSend.put("title", "Complaints Status")
                dataSend.put(
                    "content",
                    "Complaint ID $complaintIdForUpdate is updated"
                )
                val sendData = FCMSendData(
                    userToken,
                    dataSend
                )
                ifcmService.sendNotification(sendData)
                    .enqueue(object : Callback<FCMResponse?> {

                        override fun onResponse(call: Call<FCMResponse?>, response: Response<FCMResponse?>) {
                            if (response.code() == 200) {
                                if (response.body()!!.success != 1) {
                                    Toast.makeText(this@UpdateComplaint, "Failed ", Toast.LENGTH_LONG).show()
                                    finish()
                                }else
                                    finish()
                            }
                        }

                        override fun onFailure(call: Call<FCMResponse?>, t: Throwable?) {
                            finish()
                        }
                    })
                Toast.makeText(this,"Update Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun RetriveBinData() {
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                var area = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("area").getValue().toString()
                var binID = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("binID").getValue().toString()
                var complaints = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("compaintDescription").getValue().toString()
                var emailId = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("emailId").getValue().toString()
                var status = snapshot.child("Complaints").child(district).child(complaintIdForUpdate).child("status").getValue().toString()

                if (status=="Pending"){
                    complaints_status.setSelection(0)
                }else if (status=="Processing"){
                    complaints_status.setSelection(1)
                }else{
                    complaints_status.setSelection(2)
                }
                complaints_update_area.setText(area)
                complaints_update_bin_id.setText(binID)
                complaints_update_complaints.setText(complaints)
                complaints_update_email.setText(emailId)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}