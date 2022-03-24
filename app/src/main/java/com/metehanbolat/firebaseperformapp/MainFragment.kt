package com.metehanbolat.firebaseperformapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.Trace
import com.metehanbolat.firebaseperformapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ItemRecyclerAdapter
    private lateinit var itemList: ArrayList<String>
    private lateinit var firestore: FirebaseFirestore

    private lateinit var trace: Trace

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        firestore = Firebase.firestore
        itemList = ArrayList()
        adapter = ItemRecyclerAdapter(itemList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.addButton.setOnClickListener {
            val trace = Firebase.performance.newTrace("item_add_trace")
            trace.start()
            println("Trace Start")
            val hashItem = hashMapOf<String, String>()
            hashItem["item"] = binding.addEditText.text.toString()
            firestore.collection("Items").add(hashItem)
            trace.stop()
        }

        firestore.collection("Items").addSnapshotListener { value, error ->
            if (error != null) {
                println("Listen failed")
                return@addSnapshotListener
            }
            itemList.clear()
            value?.forEach {
                itemList.add(it["item"] as String)
            }
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}