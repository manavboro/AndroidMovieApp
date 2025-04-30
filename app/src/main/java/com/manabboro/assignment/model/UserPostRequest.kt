package com.manabboro.assignment.model

data class UserPostRequest(val name: String, val job: String)

data class UserResponsePost(val id: String, val name: String, val job: String, val createdAt: String)
