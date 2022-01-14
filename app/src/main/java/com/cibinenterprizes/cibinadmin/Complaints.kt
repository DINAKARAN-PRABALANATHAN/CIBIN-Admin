package com.cibinenterprizes.cibinadmin

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibinenterprizes.cibinenterprises.Model.ComplainDetails
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_complaints.*

class Complaints : AppCompatActivity() {

    private lateinit var recview: RecyclerView
    val idAuth = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaints)
        recview = findViewById(R.id.my_complaint_recyclerview)
        recview.setLayoutManager(LinearLayoutManager(this))

        my_complaints_back_botton.setOnClickListener {
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var district = snapshot.child("User Details").child(idAuth).child("Profile").child("district").getValue().toString()
                val options: FirebaseRecyclerOptions<ComplainDetails> = FirebaseRecyclerOptions.Builder<ComplainDetails>()
                    .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("Complaints").child(district!!), ComplainDetails::class.java).build()

                var adapter: FirebaseRecyclerAdapter<ComplainDetails, myviewholder> = object: FirebaseRecyclerAdapter<ComplainDetails, myviewholder>(options){
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
                        var view= LayoutInflater.from(parent.context).inflate(R.layout.my_complaint_view,parent,false)
                        return myviewholder(view)
                    }

                    override fun onBindViewHolder(holder: myviewholder, position: Int, model: ComplainDetails) {
                        holder.email?.setText(model.EmailId)
                        holder.area?.setText(model.Area)
                        holder.binId?.setText(model.BinID)
                        holder.complaint?.setText(model.CompaintDescription)
                        holder.status?.setText(model.Status)
                        holder.id?.setText(model.ComplaintId)

                        holder.itemView.setOnClickListener {
                            val complaintId = getRef(position).getKey()
                            val intent = Intent(this@Complaints,UpdateComplaint::class.java)
                            intent.putExtra("Complaint ID",complaintId)
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
        var email: TextView? = null
        var binId: TextView? = null
        var area: TextView? = null
        var complaint: TextView? = null
        var status: TextView? = null
        var id: TextView? = null

        init {
            email= itemView.findViewById(R.id.my_complaint_email)
            binId= itemView.findViewById(R.id.my_complaint_bin_id)
            area= itemView.findViewById(R.id.my_complaint_area)
            complaint= itemView.findViewById(R.id.my_complaint_complaints)
            status= itemView.findViewById(R.id.my_complaint_status)
            id= itemView.findViewById(R.id.my_complaint_id)
        }
    }
}