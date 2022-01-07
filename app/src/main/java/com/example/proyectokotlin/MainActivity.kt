package com.example.proyectokotlin

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectokotlin.model.Product
import com.example.proyectokotlin.service.ProductService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://emark-backend-nodejs.herokuapp.com"

class MainActivity : AppCompatActivity() {

    private lateinit var sqlEmark: ItemDAO;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //diseña el recyclerView (numero de columnas)
        rcView.layoutManager = GridLayoutManager(this,2)

        //Inicializamos la base de dato
        sqlEmark = ItemDAO(this)

        consultDataApi()

    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun consultDataApi(){

//        GlobalScope.launch(Dispatchers.IO) {
//            var response = getRetrofit().create(ProductService::class.java).getProducts().awaitResponse()
//            if(response.isSuccessful){
//                if(response.body() !== null){
//                    val data = response.body()!!
//                    Log.d("HAY INTER","BIENNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN")
//
//                    for ( ms in data.mensaje ){
//                        val itemLog = ItemLoading(title = ms.nombre, desc = ms.descripcion, price = ms.precioUnidad)
////                        val status = sqlEmark.insertData(itemLog)
//                        Log.d("Data: ", itemLog.title)
//                    }
//                    Log.d("RESPUESTA: ", data.ok)
//
//                    withContext(Dispatchers.Main){
//                        setListLayout(data.mensaje)
//                    }
//                }else {
//                    val data = sqlEmark.allData()
//                    setListLayout2(data)
//                }
//
//
//            }
//        }




        val data = sqlEmark.allData()
        setListLayout2(data)









    }

    fun setListLayout(products: List<Product>){
        //Crea un listado de productos del mismo tipo con la ayuda del rango
        val itemShop: List<ItemLoading> = products.map{
            ItemLoading(it.nombre, "${it.descripcion.chunked(5)[0]}...", it.precioUnidad)
        }

        //se prepara el contenido del reciclerView (AdapterLoading) y se aplica a la vista
        val adapter = AdapterLoading(itemShop)
        rcView.adapter = adapter
    }


    fun setListLayout2(products: List<ItemLoading>){

        //se prepara el contenido del reciclerView (AdapterLoading) y se aplica a la vista
        val adapter = AdapterLoading(products)
        rcView.adapter = adapter
    }


}
