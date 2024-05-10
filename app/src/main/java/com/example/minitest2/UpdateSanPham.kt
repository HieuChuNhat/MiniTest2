package com.example.minitest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import android.content.Context
import android.content.Intent

import android.text.TextUtils

import android.widget.Toast


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.google.firebase.firestore.FirebaseFirestore
import com.example.minitest2.ui.theme.MINITEST2Theme
import com.example.minitest2.ui.theme.PurpleGrey40

class UpdateSanPham : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MINITEST2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {

                            TopAppBar(backgroundColor = PurpleGrey40,

                                title = {

                                    Text(

                                        text = "Chỉnh sửa thông tin sản phẩm",

                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) { innerPadding ->
                        Text(
                            modifier = Modifier.padding(innerPadding),
                            text = "Cap nhat du lieu."
                        )


                        firebaseUI(
                            LocalContext.current,
                            intent.getStringExtra("Name"),
                            intent.getStringExtra("Type"),
                            intent.getStringExtra("Price"),
                            intent.getStringExtra("LinkImage"),
                            intent.getStringExtra("ID")
                        )


                    }
                }
            }
        }
    }


    // cap nhat
    @Composable
    fun firebaseUI(
        context: Context,
        Name: String?,
        Type: String?,
        Price: String?,
        LinkImage: String?,
        ID: String?
    ) {


        val Name = remember {
            mutableStateOf(Name)
        }

        val Type = remember {
            mutableStateOf(Type)
        }

        val Price = remember {
            mutableStateOf(Price)
        }

        val LinkImage = remember {
            mutableStateOf(LinkImage)
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            TextField(

                value = Name.value.toString(),


                onValueChange = { Name.value = it },


                placeholder = { Text(text = "Tên sản phẩm") },


                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),


                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(

                value = Type.value.toString(),


                onValueChange = { Type.value = it },


                placeholder = { Text(text = "loại sản phẩm") },


                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),


                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(

                value = Price.value.toString(),


                onValueChange = { Price.value = it },


                placeholder = { Text(text = "Giá sản phẩm") },


                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),


                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(

                value = LinkImage.value.toString(),


                onValueChange = { LinkImage.value = it },


                placeholder = { Text(text = "link ảnh sản phẩm") },


                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),


                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


                singleLine = true,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {
                    if (TextUtils.isEmpty(Name.value.toString())) {
                        Toast.makeText(context, "Nhập tên sản phẩm", Toast.LENGTH_SHORT)
                            .show()
                    } else if (TextUtils.isEmpty(Type.value.toString())) {
                        Toast.makeText(
                            context,
                            "Nhập loại sản phẩm",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else if (TextUtils.isEmpty(Price.value.toString())) {
                        Toast.makeText(
                            context,
                            "Nhập giá sản phẩm",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }else if (TextUtils.isEmpty(LinkImage.value.toString())) {
                        Toast.makeText(
                            context,
                            "Nhập link ảnh sản phẩm",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {

                        updateDataToFirebase(
                            ID,
                            Name.value,
                            Type.value,
                            Price.value,
                            LinkImage.value,
                            context
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "cập nhật sản phẩm", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    private fun updateDataToFirebase(
        ID: String?,
        Name: String?,
        Type: String?,
        Price: String?,
        LinkImage: String?,

        context: Context
    ) {

        val updatedCourse = SanPham(ID, Name, Type, Price,LinkImage)

        val db = FirebaseFirestore.getInstance();
        db.collection("SanPham").document(ID.toString()).set(updatedCourse)
            .addOnSuccessListener {

                Toast.makeText(context, "Sản phẩm câpj nhật thành công..", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))

            }.addOnFailureListener {
                Toast.makeText(context, "Cập nhật sản phẩm thất bại : " + it.message, Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

