package com.example.nutshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.nutshop.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {


    private lateinit var binding : FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.materialToolbarSearch)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navHost =
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_for_fragments) as NavHostFragment
        (activity as AppCompatActivity).setupActionBarWithNavController(navHost.navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home ->{
                findNavController().navigateUp()
                return true
            }else -> false
        }
    }
}