package com.example.nutshop.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nutshop.data.FakeProductRepository
import com.example.nutshop.domain.Category
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var homeViewModel: HomeViewModel
    @Before
    fun setup(){
        fakeProductRepository = FakeProductRepository()
        homeViewModel = HomeViewModel(fakeProductRepository)
    }

    @Test
    fun `getProductsByCategory whey protein return list`() = runBlocking{
        homeViewModel.getProductsByCategory(Category.WHEYPROTEIN)

        assertThat(homeViewModel.state.value.listOfProducts).isNotEmpty()
    }

    @Test
    fun `chooseCategory updates category to PREWORKOUT and retuns list of Pre Products`() = runBlocking {
        homeViewModel.chooseCategory(Category.PREWORKOUT)

        assertThat(homeViewModel.state.value.category).isEqualTo(Category.PREWORKOUT)
        assertThat(homeViewModel.state.value.listOfProducts.size).isEqualTo(2)
    }

    @Test
    fun `chooseCategory updates category to PROBIOTIC and returns empty list`() = runBlocking {
        homeViewModel.chooseCategory(Category.PROBIOTIC)

        assertThat(homeViewModel.state.value.category).isEqualTo(Category.PROBIOTIC)
        assertThat(homeViewModel.state.value.listOfProducts).isEmpty()
    }
}