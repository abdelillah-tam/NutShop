package com.example.nutshop.data.source

import com.example.nutshop.domain.models.Customer
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val dataSource: DataSource
): CustomerRepository {



    override fun login(email: String, password: String) : Flow<Boolean> = flow {
        dataSource.login(email, password).collect{
            emit(it)
        }
    }

    override fun signup(email: String, password: String) : Flow<Boolean> = flow {
        dataSource.signup(email, password).collect{
            emit(it)
        }
    }

    override fun getProfileInformation(): Flow<Customer?> = flow{
        dataSource.getProfileInformation().collect{
            val customer = it.toObject(Customer::class.java)
            emit(customer)
        }
    }

    override fun getProductsInShopcart(): Flow<List<Product?>> = flow{
        dataSource.getProductsInShopcart().collect{
            emit(it)
        }
    }

    override fun deleteProductFromShopcart(product: Product): Flow<Boolean> = flow{
        dataSource.deleteProductFromShopcart(product).collect{
            emit(it)
        }
    }

    override fun addToFavorite(product: Product): Flow<Boolean> = flow{
        dataSource.addToFavorite(product).collect{
            emit(it)
        }
    }

    override fun deleteFromFavorite(product: Product): Flow<Boolean> = flow{
        dataSource.addToFavorite(product).collect{
            emit(it)
        }
    }
}