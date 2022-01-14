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
import kotlinx.android.synthetic.main.activity_update_bins.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateBins : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binIdForUpdate: String
    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var database = FirebaseDatabase.getInstance().reference
    lateinit var ifcmService: IFCMService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bins)

        ifcmService = RetrofitFCMClient.getInstance("https://fcm.googleapis.com/").create(IFCMService::class.java)

        val spinner1 = findViewById<Spinner>(R.id.update_bin_status)
        val adapter1 = ArrayAdapter.createFromResource(this,R.array.status,android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.setAdapter(adapter1)
        spinner1.setOnItemSelectedListener(this)

        binIdForUpdate = getIntent().extras?.get("Bin ID").toString()
        Toast.makeText(this,binIdForUpdate, Toast.LENGTH_SHORT).show()

        RetriveBinData()
        update_bin_back_botton.setOnClickListener {
            finish()
        }
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                val userId = snapshot.child("BINS").child(district).child(binIdForUpdate).child("UserDetails").child("authId").getValue().toString()
                var userToken = snapshot.child("Tokens").child(userId).child("token").getValue().toString()
                update_bin_botton.setOnClickListener{
                    val dataSend = HashMap<String, String>()
                    dataSend.put("title", "Bin Status")
                    dataSend.put(
                        "content",
                        "Bin ID $binIdForUpdate is updated"
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
                                        Toast.makeText(this@UpdateBins, "Failed ", Toast.LENGTH_LONG).show()
                                        finish()
                                    }else
                                        finish()
                                }
                            }

                            override fun onFailure(call: Call<FCMResponse?>, t: Throwable?) {
                                finish()
                            }
                        })
                    updateBin(spinner1, userId, district)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
    private fun updateBin(spinner1: Spinner, userId: String, district: String) {

        val status = spinner1.getSelectedItem().toString()

        database.child("User Details").child(userId).child("BINS").child(binIdForUpdate).child("verification").setValue(status).addOnCompleteListener {
            database.child("BINS").child(district).child(binIdForUpdate).child("verification").setValue(status).addOnCompleteListener {

                //Toast.makeText(this,"Update Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun RetriveBinData() {
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                var area = snapshot.child("BINS").child(district).child(binIdForUpdate).child("area_Village").getValue().toString()
                var locality = snapshot.child("BINS").child(district).child(binIdForUpdate).child("locality").getValue().toString()
                var city = snapshot.child("BINS").child(district).child(binIdForUpdate).child("district").getValue().toString()
                var collectPeriod = snapshot.child("BINS").child(district).child(binIdForUpdate).child("collectionPeriod").getValue().toString()
                var loadType = snapshot.child("BINS").child(district).child(binIdForUpdate).child("loadType").getValue().toString()
                var lantitude = snapshot.child("BINS").child(district).child(binIdForUpdate).child("lantitude").getValue().toString()
                var longitude = snapshot.child("BINS").child(district).child(binIdForUpdate).child("longitude").getValue().toString()
                var status = snapshot.child("BINS").child(district).child(binIdForUpdate).child("verification").getValue().toString()

                if (status=="Pending"){
                    update_bin_status.setSelection(0)
                }else if (status=="Processing"){
                    update_bin_status.setSelection(1)
                }else{
                    update_bin_status.setSelection(2)
                }
                update_bin_area.setText(area)
                update_bin_city.setText(city)
                update_bin_collection_periods.setText(collectPeriod)
                update_bin_locality.setText(locality)
                update_bin_load_type.setText(loadType)
                update_bin_lantitude.setText(lantitude+","+longitude)
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