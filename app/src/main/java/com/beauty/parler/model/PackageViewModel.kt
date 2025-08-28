
//
//import androidx.lifecycle.ViewModel
//
//class PackageViewModel : ViewModel() {
//    private var selectedPackage: BeautyPackage? = null
//
//    fun selectPackage(beautyPackage: BeautyPackage) {
//        selectedPackage = beautyPackage
//    }
//
//    fun getSelectedPackage(): BeautyPackage? {
//        return selectedPackage
//    }
//}
//
//package com.beauty.parler.model
//
//import androidx.lifecycle.ViewModel
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.callbackFlow
//import java.util.concurrent.Flow

package com.beauty.parler.model

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PackageViewModel : ViewModel() {
    private val db = Firebase.firestore

    fun getPackageDetails(packageId: String): Flow<BeautyPackage?> = callbackFlow {
        val listener = db.collection("packages").document(packageId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val pkg = snapshot.toObject(BeautyPackage::class.java)
                    // Only use .copy if BeautyPackage has an 'id' property
                    trySend(pkg?.copy(id = snapshot.id))
                } else {
                    trySend(null)
                }
            }

        awaitClose { listener.remove() }
    }
}
