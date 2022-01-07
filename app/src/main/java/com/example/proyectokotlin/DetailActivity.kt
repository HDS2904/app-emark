package com.example.proyectokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_descr.*
import kotlinx.android.synthetic.main.item_loading.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val db = ItemDAO.getInstance(this)
        var aux: ItemLoading? = null

        //llamar datos de la anterior activity para aplicarlos al actual => opciones: ( intent.extras!!["price"],it.getString("title") )
        intent?.extras?.let {
            tvDetailTitulo.text = it.getString("title")
            tvDetailDesc.text = it.getString("desc")
            tvDetailPrice.text =  "$ ${it.getDouble("price")}"
            aux = ItemLoading(it.getString("title")+"",it.getString("desc")+"",it.getDouble("price"))
        }



        btn_detail_buy.setOnClickListener {
            try {
                db?.insertData(aux!!)
            }catch (e: Exception){
                e.printStackTrace()
                Log.e("error:","${e.message.toString()}")
            }

        }

        btn_detail_mos.setOnClickListener {
            val res: List<ItemLoading> = db!!.allData()
            var mensaje: String = ""
            for (item in res){
                mensaje+= "${item.title}, ${item.desc}, ${item.price} \n"
            }
            Log.e("mensaje datos:",mensaje)

            /*
            try {
                val res = db?.allData()
                if(res!!.count == 0){
                    Log.e("ERROR generado:", "Sin fonso")
                }

                val buffer = StringBuffer()

                while (res.moveToNext()){
                    buffer.append("ID :" + res.getString(0) + "\n")
                    buffer.append("TITULO :" + res.getString(1) + "\n")
                    buffer.append("DESCRIPCION :" + res.getString(2) + "\n")
                    buffer.append("PRECIO :" + res.getString(3) + "\n")
                }
                Log.e("DATOS A MOSTRAR: ",buffer.toString())
            }catch (e: Exception){
                e.printStackTrace()
                Log.e("error:","${e.message.toString()}")
            }*/
        }

    }
}
