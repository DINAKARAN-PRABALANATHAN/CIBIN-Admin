package com.cibinenterprizes.cibinadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import com.cibinenterprizes.cibinadmin.Model.FCMResponse
import com.cibinenterprizes.cibinadmin.Model.FCMSendData
import com.cibinenterprizes.cibinadmin.Remote.IFCMService
import com.cibinenterprizes.cibinadmin.Remote.RetrofitFCMClient
import com.cibinenterprizes.cibinadmin.Model.BinDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_work_acceptance.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkAcceptance : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var handler: Handler
    private lateinit var requestIdForUpdate: String
    var bin1: String? = null
    var bin2: String? = null
    var bin3: String? = null
    var bin4: String? = null
    var bin5: String? = null
    var bin6: String? = null
    var bin7: String? = null
    var bin8: String? = null
    var bin9: String? = null
    var bin10: String? = null
    var bin11: String? = null
    var bin12: String? = null
    var driverName: String? = null
    var binDetails1: BinDetails? = null
    var binDetails2: BinDetails? = null
    var binDetails3: BinDetails? = null
    var binDetails4: BinDetails? = null
    var binDetails5: BinDetails? = null
    var binDetails6: BinDetails? = null
    var binDetails7: BinDetails? = null
    var binDetails8: BinDetails? = null
    var binDetails9: BinDetails? = null
    var binDetails10: BinDetails? = null
    var binDetails11: BinDetails? = null
    var binDetails12: BinDetails? = null
    var userId: String? = null
    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var database = FirebaseDatabase.getInstance().reference
    lateinit var ifcmService: IFCMService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_acceptance)

        ifcmService = RetrofitFCMClient.getInstance("https://fcm.googleapis.com/").create(IFCMService::class.java)

        val spinner1 = findViewById<Spinner>(R.id.work_update_status)
        val adapter1 = ArrayAdapter.createFromResource(this,R.array.status,android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.setAdapter(adapter1)
        spinner1.setOnItemSelectedListener(this)

        requestIdForUpdate = getIntent().extras?.get("Bin ID").toString()
        Toast.makeText(this,requestIdForUpdate, Toast.LENGTH_SHORT).show()

        RetriveBinData()

        work_update_back_botton.setOnClickListener {
            finish()
        }


        work_update_submit_botton.setOnClickListener{
            if(work_update_bin_1.isChecked){
                if(bin1!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin1!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin1!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin1!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin1!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin1!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin1!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin1!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin1!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails1 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin1!!).setValue(binDetails1).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin1!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin1 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_2.isChecked){
                if(bin2!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin2!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin2!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin2!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin2!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin2!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin2!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin2!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin2!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails2 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin2!!).setValue(binDetails2).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin2!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin2 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_3.isChecked){
                if(bin3!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin3!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin3!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin3!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin3!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin3!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin3!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin3!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin3!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails3 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin3!!).setValue(binDetails3).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin3!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin3 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_4.isChecked){
                if(bin4!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin4!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin4!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin4!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin4!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin4!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin4!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin4!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin4!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails4 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin4!!).setValue(binDetails4).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin4!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin4 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_5.isChecked){
                if(bin5!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin5!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin5!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin5!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin5!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin5!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin5!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin5!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin5!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails5 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin5!!).setValue(binDetails5).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin5!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin5 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_6.isChecked){
                if(bin6!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin6!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin6!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin6!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin6!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin6!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin6!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin6!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin6!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails6 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin6!!).setValue(binDetails6).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin6!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin6 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_7.isChecked){
                if(bin7!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin7!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin7!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin7!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin7!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin7!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin7!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin7!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin7!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails7 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin7!!).setValue(binDetails7).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin7!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin7 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_8.isChecked){
                if(bin8!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin8!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin8!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin8!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin8!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin8!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin8!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin8!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin8!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails8 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin8!!).setValue(binDetails8).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin8!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin8 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_9.isChecked){
                if(bin9!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin9!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin9!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin9!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin9!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin9!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin9!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin9!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin9!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails9 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin9!!).setValue(binDetails9).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin9!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin9 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_10.isChecked){
                if(bin10!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin10!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin10!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin10!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin10!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin10!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin10!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin10!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin10!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails10 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin10!!).setValue(binDetails10).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin10!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin10 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_11.isChecked){
                if(bin11!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin11!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin11!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin11!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin11!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin11!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin11!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin11!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin11!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails11 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin11!!).setValue(binDetails11).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin11!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin11 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            if(work_update_bin_12.isChecked){
                if(bin12!=null){
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                            var binId: String = snapshot.child("BINS").child(district).child(bin12!!).child("binId").getValue().toString()
                            var area = snapshot.child("BINS").child(district).child(bin12!!).child("area_Village").getValue().toString()
                            var city = snapshot.child("BINS").child(district).child(bin12!!).child("district").getValue().toString()
                            var locality = snapshot.child("BINS").child(district).child(bin12!!).child("locality").getValue().toString()
                            var collectionPeriod = snapshot.child("BINS").child(district).child(bin12!!).child("collectionPeriod").getValue().toString()
                            var loadType = snapshot.child("BINS").child(district).child(bin12!!).child("loadType").getValue().toString()
                            var lantitude = snapshot.child("BINS").child(district).child(bin12!!).child("lantitude").getValue().toString()
                            var longitude = snapshot.child("BINS").child(district).child(bin12!!).child("longitude").getValue().toString()
                            var status = "Verified"
                            binDetails12 = BinDetails(area,locality,city,loadType,collectionPeriod,lantitude,longitude,binId.toInt(),status)
                            database.child("User Details").child(userId!!).child("Work Todo").child(bin12!!).setValue(binDetails12).addOnCompleteListener {
                                database.child("BINS").child(district).child(bin12!!).child("driverName").setValue(driverName).addOnCompleteListener {
                                    Log.i("WorkAcceptance", "bin12 completed")
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            updateBin(spinner1)
        }
    }
    private fun updateBin(spinner1: Spinner) {

        val status = spinner1.getSelectedItem().toString()

        database.child("Request Bins").child(requestIdForUpdate).child("verification").setValue(status).addOnCompleteListener {
            database.child("User Details").child(userId!!).child("Request Bins").child(requestIdForUpdate).child("verification").setValue("Verified").addOnCompleteListener {
                Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var adminToken = snapshot.child("Tokens").child(userId!!).child("token").getValue().toString()
                        val dataSend = HashMap<String, String>()
                        dataSend.put("title", "New Work")
                        dataSend.put(
                            "content",
                            "You have new Bin to work, Please check it..."
                        )
                        val sendData = FCMSendData(
                            adminToken,
                            dataSend
                        )
                        ifcmService.sendNotification(sendData)
                            .enqueue(object : Callback<FCMResponse?> {

                                override fun onResponse(call: Call<FCMResponse?>, response: Response<FCMResponse?>) {
                                    if (response.code() == 200) {
                                        if (response.body()!!.success != 1) {
                                            Toast.makeText(this@WorkAcceptance, "Failed ", Toast.LENGTH_LONG).show()
                                            finish()
                                        }else
                                            finish()
                                    }
                                }

                                override fun onFailure(call: Call<FCMResponse?>, t: Throwable?) {
                                    finish()
                                }
                            })
                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

            }
        }
    }
    private fun RetriveBinData() {
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(user?.uid.toString()).child("Profile").child("district").getValue().toString()
                userId = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("driverId").getValue().toString()
                driverName = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("driverName").getValue().toString()
                var driverMobile = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("driverMobile").getValue().toString()
                bin1 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin1").getValue().toString()
                bin2 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin2").getValue().toString()
                bin3 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin3").getValue().toString()
                bin4 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin4").getValue().toString()
                bin5 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin5").getValue().toString()
                bin6 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin6").getValue().toString()
                bin7 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin7").getValue().toString()
                bin8 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin8").getValue().toString()
                bin9 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin9").getValue().toString()
                bin10 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin10").getValue().toString()
                bin11 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin11").getValue().toString()
                bin12 = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("bin12").getValue().toString()
                var status = snapshot.child("Request Bins").child(district).child(requestIdForUpdate).child("verification").getValue().toString()

                if (status=="Pending"){
                    work_update_status.setSelection(0)
                }else if (status=="Processing"){
                    work_update_status.setSelection(1)
                }else{
                    work_update_status.setSelection(2)
                }

                work_update_driver_name.setText(driverName)
                work_update_driver_mobile.setText(driverMobile)
                work_update_bins_1.setText(bin1)
                work_update_bins_2.setText(bin2)
                work_update_bins_3.setText(bin3)
                work_update_bins_4.setText(bin4)
                work_update_bins_5.setText(bin5)
                work_update_bins_6.setText(bin6)
                work_update_bins_7.setText(bin7)
                work_update_bins_8.setText(bin8)
                work_update_bins_9.setText(bin9)
                work_update_bins_10.setText(bin10)
                work_update_bins_11.setText(bin11)
                work_update_bins_12.setText(bin12)
                work_update_request_id.setText(requestIdForUpdate)
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