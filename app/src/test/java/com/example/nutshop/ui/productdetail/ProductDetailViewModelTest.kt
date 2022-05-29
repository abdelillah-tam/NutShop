package com.example.nutshop.ui.productdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nutshop.data.FakeProductRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.bouncycastle.util.test.SimpleTest.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductDetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var productDetailViewModel: ProductDetailViewModel


    @Before
    fun setup(){
        fakeProductRepository = FakeProductRepository()
        productDetailViewModel = ProductDetailViewModel(fakeProductRepository)
    }

    @Test
    fun `addToCart JYM product returns true and quantity equals to 56`() = runBlocking{
        productDetailViewModel.showProductOnScreen(fakeProductRepository.list[1])
        productDetailViewModel.addToCart(fakeProductRepository.list[1], 2)


        assertThat(productDetailViewModel.state.value.addedToCart).isTrue()
        assertThat(fakeProductRepository.shopCart[0].quantity).isEqualTo(2)
        assertThat(productDetailViewModel.state.value.quantity).isEqualTo(56)
    }

    @Test
    fun `addToCart JYM product returns false and quantity greater than current quantity`() = runBlocking{
        productDetailViewModel.showProductOnScreen(fakeProductRepository.list[1])
        productDetailViewModel.addToCart(fakeProductRepository.list[1], 60)

        assertThat(productDetailViewModel.state.value.addedToCart).isFalse()
    }
}