package com.cibinenterprizes.cibinadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibinenterprizes.cibindrivers.Model.RequestDetails
import com.cibinenterprizes.cibinadmin.Model.BinDetails
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_work_assigning.*

class WorkAssigning : AppCompatActivity() {
    private lateinit var recview: RecyclerView
    val idAuth = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_assigning)
        recview = findViewById(R.id.work_assigning_recyclerview)
        recview.setLayoutManager(LinearLayoutManager(this))

        work_assigning_back_botton.setOnClickListener {
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(idAuth).child("Profile").child("district").getValue().toString()
                val options: FirebaseRecyclerOptions<RequestDetails> = FirebaseRecyclerOptions.Builder<RequestDetails>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Request Bins").child(district), RequestDetails::class.java).build()

                var adapter: FirebaseRecyclerAdapter<RequestDetails, myviewholder> =object: FirebaseRecyclerAdapter<RequestDetails, myviewholder>(options){
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
                        var view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_assigning_single_view, parent, false)
                        return myviewholder(view)
                    }

                    override fun onBindViewHolder(holder: myviewholder, position: Int, model: RequestDetails) {
                        holder.verificationStatus?.setText(model.Verification)
                        holder.requestID?.setText(getRef(position).key)
                        holder.driverName?.setText(model.DriverName)
                        holder.driverMobile?.setText(model.DriverMobile)
                        holder.driverID?.setText(model.DriverId)
                        holder.itemView.setOnClickListener {
                            val requestId = getRef(position).getKey()
                            val intent = Intent(this@WorkAssigning,WorkAcceptance::class.java)
                            intent.putExtra("Bin ID",requestId)
                            startActivity(intent)
                            finish()
                        }
                    }

                }
                recview.setAdapter(adapter)
                adapter.startListening()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
    class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var verificationStatus: TextView? = null
        var requestID: TextView? = null
        var driverID: TextView? = null
        var driverName: TextView? = null
        var driverMobile: TextView? = null

        init {

            verificationStatus= itemView.findViewById(R.id.request_driver_verification)
            requestID= itemView.findViewById(R.id.request_id)
            driverID= itemView.findViewById(R.id.request_driver_ID)
            driverMobile= itemView.findViewById(R.id.request_driver_mobile_number)
            driverName= itemView.findViewById(R.id.request_driver_name)
            super.itemView
        }
    }
}