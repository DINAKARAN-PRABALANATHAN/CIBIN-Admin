package com.cibinenterprizes.cibinadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibinenterprizes.cibinadmin.Model.DriverDetails
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_bins.*
import kotlinx.android.synthetic.main.activity_bins.list_of_bin_back_botton
import kotlinx.android.synthetic.main.activity_drivers.*

class Drivers : AppCompatActivity() {
    private lateinit var recview: RecyclerView
    val idAuth = FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)

        recview = findViewById(R.id.driver_recyclerview)
        recview.setLayoutManager(LinearLayoutManager(this))

        driver_back_botton.setOnClickListener {
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        val options: FirebaseRecyclerOptions<DriverDetails> = FirebaseRecyclerOptions.Builder<DriverDetails>()
            .setQuery(
                FirebaseDatabase.getInstance().getReference().child("Drivers"), DriverDetails::class.java).build()

        var adapter: FirebaseRecyclerAdapter<DriverDetails, myviewholder> =object: FirebaseRecyclerAdapter<DriverDetails, myviewholder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
                var view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_single_view, parent, false)
                return myviewholder(view)
            }

            override fun onBindViewHolder(holder: myviewholder, position: Int, model: DriverDetails) {
                holder.driverId?.setText(model.CDid)
                holder.driverName?.setText(model.Username.toString())
                holder.driverEmail?.setText(model.EmailId)
                holder.driverMobile?.setText(model.Mobile)
            }

        }
        recview.setAdapter(adapter)
        adapter.startListening()
    }
    class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var driverId: TextView? = null
        var driverName: TextView? = null
        var driverEmail: TextView? = null
        var driverMobile: TextView? = null

        init {
            driverId= itemView.findViewById(R.id.driver_id)
            driverName= itemView.findViewById(R.id.driver_name)
            driverEmail= itemView.findViewById(R.id.driver_email)
            driverMobile= itemView.findViewById(R.id.driver_mobile_number)
            super.itemView
        }
    }
}