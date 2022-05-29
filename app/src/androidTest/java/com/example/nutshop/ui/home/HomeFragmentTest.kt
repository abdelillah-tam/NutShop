package com.example.nutshop.ui.home

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.nutshop.R
import com.example.nutshop.data.source.ProductRepository
import com.example.nutshop.di.AppModule
import com.example.nutshop.domain.Category
import com.google.common.truth.Truth.assertThat
import com.example.nutshop.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class)
@HiltAndroidTest
class HomeFragmentTest{


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var repository: ProductRepository
    @Inject lateinit var adapter: HomeRecyclerAdapter


    @Before
    fun init(){
        hiltRule.inject()

    }

    @Test
    fun displayListOfProducts() = runTest{
        lateinit var recyclerView: RecyclerView
        launchFragmentInHiltContainer<HomeFragment> {
            recyclerView = this.activity!!.findViewById(R.id.recyclerlist_products_home)
        }
        recyclerView.adapter = adapter
        repository.getProductsByCategory(Category.WHEYPROTEIN).collect{
            adapter.setData(it)
        }
        onView(withId(R.id.recyclerlist_products_home)).perform(actionOnItemAtPosition<HomeRecyclerAdapter.ViewHolder>(0, click()))

    }


    @Test
    fun radioGroupFirstOptionClicked() {
        launchFragmentInHiltContainer<HomeFragment>()
        onView(withId(R.id.category_whey)).perform(click())
        onView(withId(R.id.category_whey)).check(matches(isChecked()))
        onView(withId(R.id.category_fat)).check(matches(isNotChecked()))
        onView(withId(R.id.category_pre)).check(matches(isNotChecked()))
        onView(withId(R.id.category_prob)).check(matches(isNotChecked()))
        onView(withId(R.id.category_mass)).check(matches(isNotChecked()))
    }

    @Test
    fun testNavigationToProductDetailFragment() = runTest{
        lateinit var recyclerView: RecyclerView
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        launchFragmentInHiltContainer<HomeFragment>(){
            recyclerView = this.activity!!.findViewById(R.id.recyclerlist_products_home)
            navController.setGraph(R.navigation.nav_nutshop)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        recyclerView.adapter = adapter
        repository.getProductsByCategory(Category.WHEYPROTEIN).collect{
            adapter.setData(it)
        }

        onView(withId(R.id.recyclerlist_products_home)).perform(actionOnItemAtPosition<HomeRecyclerAdapter.ViewHolder>(0,click()))
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.productDetailFragment)

    }
}