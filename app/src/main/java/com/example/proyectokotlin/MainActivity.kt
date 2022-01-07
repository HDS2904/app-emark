package com.example.proyectokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //diseña el recyclerView (numero de columnas)
        rcView.layoutManager = GridLayoutManager(this,2)

        //Crea un listado de productos del mismo tipo con la ayuda del rango
        val itemShop: List<ItemLoading> = (0..20).map{
            ItemLoading("Titulo $it", "Desc $it", 200.00 + it)
        }

        //se prepara el contenido del reciclerView (AdapterLoading) y se aplica a la vista
        val adapter = AdapterLoading(itemShop)
        rcView.adapter = adapter

    }

}
