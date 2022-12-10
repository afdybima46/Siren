package com.example.siren.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.siren.adapter.ArticleAdapter
import com.example.siren.databinding.FragmentHomeBinding
import com.example.siren.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var databases: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        loadUser()

        val bundle = Bundle()
        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(Constants.TAB_TITLES[position])
        }.attach()
    }

    private fun loadUser() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvUser.text = snapshot.child("Name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}