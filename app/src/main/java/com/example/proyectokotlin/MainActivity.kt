package com.example.proyectokotlin

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import java.lang.Exception
import kotlin.properties.Delegates

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
//        getData() //SIN ITNER
        GlobalScope.launch(Dispatchers.IO) {
            try {
                var response = getRetrofit().create(ProductService::class.java).getProducts().awaitResponse()
                if(response.isSuccessful){
                    if(response.body() !== null){
                        val data = response.body()!!

                        for ( ms in data.mensaje ){
                            val itemLog = ItemLoading(title = ms.nombre, desc = ms.descripcion, price = ms.precioUnidad)
                            sqlEmark.insertData(itemLog)
                            Log.d("Data: ", itemLog.title)
                        }
                        Log.d("RESPUESTA: ", data.ok)

                        withContext(Dispatchers.Main){
                            setListLayout(data.mensaje)
                        }
                    }else {
                        val data = sqlEmark.allData()
                        setListLayout2(data)
                    }


                }
            }catch (error: Exception){
                Log.d("SIN INTER","NOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
            }
        }

    }

    fun getData() {
        Handler().postDelayed({
            val data = sqlEmark.allData()
            setListLayout2(data)
        }, 5000)
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
