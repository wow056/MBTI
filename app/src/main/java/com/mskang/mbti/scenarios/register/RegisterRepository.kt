package com.mskang.mbti.scenarios.register

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor() {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore

    suspend fun register(email: String, password: String, nickname: String, birth: String, sex: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
        fireStore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .set(
                mapOf(
                    "nickname" to nickname,
                    "birth" to birth,
                    "sex" to sex
                )
            )
            .await()
    }

    suspend fun updateMBTI(knowMBTIValue: Boolean, i: Int, e: Int, n: Int, s: Int, t: Int, f: Int, j: Int, p: Int) {
        fireStore
            .collection("users")
            .document(auth.currentUser!!.uid)
            .set(
                mapOf(
                    "knowMBTIValue" to knowMBTIValue,
                    "i" to i,
                    "e" to e,
                    "n" to n,
                    "s" to s,
                    "t" to t,
                    "f" to f,
                    "j" to j,
                    "p" to p
                ),
                SetOptions.merge()
            )
            .await()
    }
}