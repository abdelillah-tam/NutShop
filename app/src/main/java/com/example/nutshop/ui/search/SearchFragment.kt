package com.example.nutshop.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "SearchFragment"
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {


    private lateinit var binding : FragmentSearchBinding

    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var searchListAdapter: SearchListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        initToolbar()

        searchListAdapter = SearchListAdapter()

        binding.listSearchedProducts.let {
            it.adapter = searchListAdapter
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        binding.searchedittextfr.requestFocus()

        binding.searchedittextfr.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) searchViewModel.clearList()
                else searchViewModel.searchForProducts(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        lifecycleScope.launch {
            searchViewModel.state.collect{
                searchListAdapter.setData(it.list)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home ->{
                findNavController().navigateUp()
                return true
            }else -> false
        }
    }

    fun initToolbar(){
        setHasOptionsMenu(true)
        val mActivity = activity as AppCompatActivity
        mActivity.setSupportActionBar(binding.materialToolbarSearch)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navHost =
            mActivity.supportFragmentManager.findFragmentById(R.id.fragment_container_for_fragments) as NavHostFragment
        mActivity.setupActionBarWithNavController(navHost.navController)
        binding.materialToolbarSearch.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        binding.materialToolbarSearch.setNavigationIconTint(resources.getColor(R.color.blue_900))
    }
}