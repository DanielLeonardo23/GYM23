package com.example.gym23.Entity

import java.io.Serializable

data class UserRecord(
    val token: String,
    val record: Record
) : Serializable {
    companion object {
        fun fake(email: String, tel: String): UserRecord {
            return UserRecord(
                token = "fakeToken123",
                record = Record(
                    id = "0",
                    collectionId = "fakeCollection",
                    collectionName = "users",
                    username = "fakeuser",
                    description = "Fake user for preview",
                    verified = false,
                    emailVisibility = true,
                    email = email,
                    created = "2025-01-01",
                    updated = "2025-01-01",
                    name = "Fake User",
                    telephone = tel,
                    avatar = "https://example.com/avatar.png"
                )
            )
        }
    }
}

data class Record(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val username: String,
    val description: String,
    val verified: Boolean,
    val emailVisibility: Boolean,
    val email: String,
    val created: String,
    val updated: String,
    val name: String,
    val telephone: String,
    val avatar: String
) : Serializable
