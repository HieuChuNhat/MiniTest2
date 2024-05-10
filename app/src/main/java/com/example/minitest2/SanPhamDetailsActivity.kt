package com.example.minitest2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.example.minitest2.ui.theme.MINITEST2Theme

class CourseDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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

                            TopAppBar(

                                title = {

                                    Text(

                                        text = "Dữ liệu sản phẩm",

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


                        var courseList = mutableStateListOf<SanPham?>()

                        var db: FirebaseFirestore = FirebaseFirestore.getInstance()


                        db.collection("SanPham").get()
                            .addOnSuccessListener { queryDocumentSnapshots ->

                                if (!queryDocumentSnapshots.isEmpty) {

                                    val list = queryDocumentSnapshots.documents
                                    for (d in list) {

                                        val c: SanPham? = d.toObject(SanPham::class.java)
                                        c?.ID = d.id
                                        Log.e("TAG", "ID sản phẩm là : " + c!!.ID)

                                        courseList.add(c)

                                    }
                                } else {

                                    Toast.makeText(
                                        this@CourseDetailsActivity,
                                        "Không có dữ liệu",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            .addOnFailureListener {
                                Toast.makeText(
                                    this@CourseDetailsActivity,
                                    "Dữ liẹu bị lỗi.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        Spacer(modifier = Modifier.size(15.dp))
                        firebaseUI(LocalContext.current, courseList)
                    }
                }
            }
        }
    }


}

private fun deleteDataFromFirebase(ID: String?, context: Context) {

    val db = FirebaseFirestore.getInstance();


    db.collection("SanPham").document(ID.toString()).delete().addOnSuccessListener {

        Toast.makeText(context, "Sản phẩm xóa thành công..", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, CourseDetailsActivity::class.java))
    }.addOnFailureListener {

        Toast.makeText(context, "Xóa sản phẩm thất bại ..", Toast.LENGTH_SHORT).show()
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun firebaseUI(context: Context, courseList: SnapshotStateList<SanPham?>) {


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {

            itemsIndexed(courseList) { index, item ->

                Card(
                    onClick = {
                        val i = Intent(context, UpdateSanPham::class.java)
                        i.putExtra("ID", item?.ID)
                        i.putExtra("Name", item?.Name)
                        i.putExtra("Type", item?.Type)
                        i.putExtra("Price", item?.Price)
                        i.putExtra("LinkImage", item?.LinkImage)

                        context.startActivity(i)

                    },

                    modifier = Modifier.padding(8.dp),


                    elevation = 6.dp
                ) {

                    Column(

                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {

                        Spacer(modifier = Modifier.size(15.dp))

                        courseList[index]?.Name?.let {
                            Text(

                                text = it,


                                modifier = Modifier.padding(4.dp),


                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                        courseList[index]?.Name?.let {
                            Text(

                                text = "Tên sản phẩm : $it",

                                modifier = Modifier.padding(4.dp),

                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        courseList[index]?.Type?.let {
                            Text(

                                text = "Loại sản Phẩm :$it",


                                modifier = Modifier.padding(4.dp),

                                // on below line we are adding color for our text
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        courseList[index]?.Price?.let {
                            Text(

                                text = "Giá sản phẩm : $it",


                                modifier = Modifier.padding(4.dp),

                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 15.sp
                                )
                            )
                        }


                        Spacer(modifier = Modifier.width(5.dp))

                        courseList[index]?.LinkImage?.let {

                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(RoundedCornerShape(size = 8.dp)),
                                painter = rememberAsyncImagePainter(it),
                                contentScale = ContentScale.Crop,
                                contentDescription = "hinh anh"
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {


                                deleteDataFromFirebase(courseList[index]?.ID, context)
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = "Xóa sản phẩm", modifier = Modifier.padding(8.dp))
                        }




                    }
                }
            }


        }
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

            Toast.makeText(context, "Sản phẩm cập nhật thành công..", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, CourseDetailsActivity::class.java))


        }.addOnFailureListener {
            Toast.makeText(context, "Cập nhật sản phẩm thất bại : " + it.message, Toast.LENGTH_SHORT)
                .show()
        }

}



