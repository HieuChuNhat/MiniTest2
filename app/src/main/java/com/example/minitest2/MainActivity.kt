package com.example.minitest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface


import androidx.compose.material3.TextField

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.NotificationCompat.Style
import com.example.minitest2.ui.theme.MINITEST2Theme
import com.example.minitest2.ui.theme.PurpleGrey40

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID


class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
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
                                        text = "Đữ liệu sản phẩm",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) {innerPadding ->
                        Text(
                            modifier = Modifier.padding(innerPadding),
                            text = "Them du lieu."
                        )
                        FirebaseUI(LocalContext.current)
                    }
                }
            }
        }
    }
}


@Composable
fun FirebaseUI(context: Context) {

    val ID = remember {
        mutableStateOf("")
    }
    val Name = remember {
        mutableStateOf("")
    }

    val Type  = remember {
        mutableStateOf("")
    }

    val LinkImage  = remember {
        mutableStateOf("")
    }

    val Price   = remember {
        mutableStateOf("")
    }

    Column(
        // adding modifier for our column
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),

    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(text = "Tên sản phẩm :",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )

        TextField(
      value = Name.value,


            onValueChange = { Name.value = it },


            placeholder = { Text(text = "Tên sản phẩm") },


            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),


            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))


        Text(text = "Loại sản phẩm :",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )
        TextField(

            value = Type .value,


            onValueChange = { Type .value = it },


            placeholder = { Text(text = "loại sản phẩm") },


            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),


            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


            singleLine = true,
        )


        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Giá sản phẩm :",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )

        TextField(

            value = Price.value,


            onValueChange = { Price.value = it },


            placeholder = { Text(text = "Giá") },


            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),


            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


            singleLine = true,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Link ảnh sản phẩm :",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )

        TextField(

            value = LinkImage .value,


            onValueChange = { LinkImage .value = it },


            placeholder = { Text(text = "Link ảnh") },


            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),


            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),


            singleLine = true,
        )


        Spacer(modifier = Modifier.height(5.dp))


        Button(

            onClick = {
                if (TextUtils.isEmpty(Name.value.toString())) {
                    Toast.makeText(context, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(Type .value.toString())) {
                    Toast.makeText(context, "Vui lòng nhập loại sản phẩm", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty( Price.value.toString())) {
                    Toast.makeText(context, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(LinkImage .value.toString())){
                    Toast.makeText(context, "Vui lòng nhập link ảnh sản phẩm", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    ID.value  = UUID.randomUUID().toString()
                    addDataToFirebase(ID.value, Name.value, Type .value, Price .value , LinkImage .value, context)

                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

        ) {
            Text(text = "Thêm sản phẩm", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(5.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Xem danh sách dữ liệu sản phẩm", modifier = Modifier.padding(8.dp))
        }


    }
}


fun addDataToFirebase(
    ID:String,Name: String, Type : String, Price : String , LinkImage : String, context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    val dbSanPham: CollectionReference = db.collection("SanPham")


    val SanPham = SanPham(ID,Name, Type,Price ,LinkImage )


    dbSanPham.add(SanPham).addOnSuccessListener {
        Toast.makeText(
            context, "Sản phẩm của bạn đã được thêm vào Firebase Firestore", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->

        Toast.makeText(context, "Không thể thêm sản phẩm \n$e", Toast.LENGTH_SHORT).show()
    }

}