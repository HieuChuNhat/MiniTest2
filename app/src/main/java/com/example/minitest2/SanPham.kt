package com.example.minitest2

import com.google.firebase.firestore.Exclude

data class SanPham(
    @Exclude var ID: String? = "",
    var Name: String? = "",
    var Type: String? = "",
    var Price: String? = "",
    var LinkImage: String? = ""
)