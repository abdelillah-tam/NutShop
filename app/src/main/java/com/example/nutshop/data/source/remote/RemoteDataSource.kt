package com.example.nutshop.data.source.remote

import android.util.Log
import com.example.nutshop.data.source.DataSource
import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Customer
import com.example.nutshop.domain.models.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

private const val TAG = "RemoteDataSource"

object RemoteDataSource : DataSource {

    private val productsFirestore = Firebase.firestore.collection("products")
    private val customerFirestore = Firebase.firestore.collection("customer")
    private val auth = Firebase.auth

    override fun getProductsByCategory(category: Category): Flow<List<Product?>> = flow {
        val products = productsFirestore.whereEqualTo("category", category).get().await()
            .toObjects(Product::class.java)
        emit(products)
    }

    override fun addToCart(product: Product, quantityYouWant: Int): Flow<Boolean> = flow {
        calculateQuantity(product, quantityYouWant).collect {
            emit(it)
        }
    }

    override fun login(email: String, password: String): Flow<Boolean> = callbackFlow {
        if (auth.currentUser == null) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(true)
                }
            }.addOnFailureListener {
                trySend(false)
            }
        }
        awaitClose {
            this.cancel()
        }
    }

    override fun signup(email: String, password: String): Flow<Boolean> = callbackFlow {
        if (auth.currentUser == null) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val customer = Customer(
                        uid = auth.currentUser!!.uid,
                        email = email,
                        firstName = "",
                        lastName = "",
                        address = ""
                    )
                    customerFirestore.document(auth.currentUser!!.uid).set(customer)
                    trySend(true)
                }
            }.addOnFailureListener {
                trySend(false)
            }


        }
        awaitClose {
            this.cancel()
        }
    }

    override fun getProfileInformation(): Flow<DocumentSnapshot> = flow {
        val result = customerFirestore.document(auth.currentUser!!.uid).get().await()
        emit(result)
    }

    private fun calculateQuantity(product: Product, quantityYouWant: Int): Flow<Boolean> = flow {
        var result = false

        product.quantity -= quantityYouWant
        product.quantityTaken += quantityYouWant


        customerFirestore.document(auth.currentUser!!.uid)
                .collection("shopcart")
                .document(product.productId).set(product).addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = true
                    }
                }.addOnFailureListener {
                    result = false
                }.await()


        productsFirestore.document(product.productId).update(
            mapOf(
                "quantity" to product.quantity,
                "quantityTaken" to product.quantityTaken
            )
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                result = true
            }
        }.addOnFailureListener {
            result = false
        }.await()



        emit(result)
    }

    override fun getProductsInShopcart() : Flow<List<Product?>> = callbackFlow{
        customerFirestore.document(auth.currentUser!!.uid)
            .collection("shopcart")
            .addSnapshotListener { value, error ->
                trySend(value!!.toObjects(Product::class.java))
            }

        awaitClose {
            this.cancel()
        }
    }

    override fun deleteProductFromShopcart(product: Product) : Flow<Boolean> = flow{
        var result = false
        customerFirestore.document(auth.currentUser!!.uid)
            .collection("shopcart")
            .document(product.productId).delete()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    product.quantity+=product.quantityTaken
                    product.quantityTaken = 0
                    productsFirestore.document(product.productId).update(
                        mapOf(
                            "quantity" to product.quantity,
                            "quantityTaken" to product.quantityTaken
                        )
                    )
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }.await()

        emit(result)

    }

    override fun getProductsInFavorite(): Flow<List<Product?>> = callbackFlow{
        customerFirestore.document(auth.currentUser!!.uid)
            .collection("favorite")
            .addSnapshotListener { value, error ->
                trySend(value!!.toObjects(Product::class.java))
            }

        awaitClose {
            this.close()
        }
    }

    override fun addToFavorite(product: Product): Flow<Boolean> = flow{
        var result = false
        product.favorite = true
        customerFirestore.document(auth.currentUser!!.uid)
            .collection("favorite")
            .document(product.productId)
            .set(product)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }.await()

        productsFirestore.document(product.productId)
            .update("favorite", true)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }.await()

        emit(result)
    }

    override fun deteleFromFavorite(product: Product): Flow<Boolean> = flow{
        var result = false
        product.favorite = false
        customerFirestore.document(auth.currentUser!!.uid)
            .collection("favorite")
            .document(product.productId)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }.await()

        productsFirestore.document(product.productId)
            .update("favorite", false)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }.await()

        emit(result)
    }
}