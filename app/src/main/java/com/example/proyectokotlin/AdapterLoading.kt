package com.example.proyectokotlin

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_loading.view.*

class AdapterLoading (val data: List<ItemLoading>): RecyclerView.Adapter<AdapterLoading.Holder>(){

    //Crea la instancia  .xml con su respectivo contenido administrado por Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent.inflate(R.layout.item_loading))

    //Obtiene la cantidad de datos
    override fun getItemCount(): Int = data.size

    //Ejecuta el llenado de datos de cada articulo
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(data[position])
    }

    //clase con la funcion administrado de contenido de los artículos
    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(itemLoading: ItemLoading) {
            
            //carga de datos a las propiedades de item detail
            with(itemLoading){

                itemView.txtTitle.text = title
                itemView.txtDesc.text = desc
                itemView.txtPrice.text = String.format("%.2f",price)

                itemView.setOnClickListener{

                    //inicializacion del activity a pasar y carga de datos a enviar
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("desc", desc)
                    intent.putExtra("price", price)

                    //desarrollo de transiciones de elementos animados
                    val p1: Pair<View, String> = Pair.create(itemView.imgHeader,"transitionHeader")
                    val p2: Pair<View, String> = Pair.create(itemView.txtTitle,"transitionTitle")
                    val p3: Pair<View, String> = Pair.create(itemView.txtDesc,"transitionDesc")
                    val p4: Pair<View, String> = Pair.create(itemView.txtPrice,"transitionPrice")

                    val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity,p1,p2,p3,p4)

                    //iniciar el cambio de activity con datos y transiciones
                    itemView.context.startActivity(intent,options.toBundle())
                }
            }
        }
    }
}