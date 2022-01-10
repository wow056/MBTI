package com.mskang.mbti.scenarios.board

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BoardRepository @Inject constructor() {
    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth

    suspend fun getPostList(tagSelection: List<String>): List<BoardListItem> {
        if (tagSelection.isEmpty()) {
            return emptyList()
        }

        return fireStore
            .collection("posts")
            .whereArrayContainsAny("tags", tagSelection)
            .get()
            .await()
            .map {
                val user = (it["user"] as DocumentReference).get().await()
                BoardListItem(
                    id = it.id,
                    title = it["title"] as String,
                    content = it["content"] as String,
                    mbti = "INFP",
                    nickname = user["nickname"] as String,
                    likes = (it["likedUsers"] as? List<*>)?.size ?: 0,
                    comments = (it["comments"] as? List<*>)?.size ?: 0
                )
            }
    }

    suspend fun postPost(title: String, content: String, tags: List<String>) {
        fireStore
            .collection("posts")
            .document()
            .set(
                mapOf(
                    "user" to fireStore.document("users/${auth.currentUser!!.uid}"),
                    "title" to title,
                    "content" to content,
                    "tags" to tags,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            .await()
    }
}