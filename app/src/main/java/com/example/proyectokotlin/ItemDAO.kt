package com.example.proyectokotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class ItemDAO (private val context: Context): SQLiteOpenHelper(context, dbName, null, 1){

    companion object {

        //variables de la tabla
        val dbName = "SHOPDATABASE"
        val tableName = "PRODUCTO"
        val col1 = "ID"
        val col2 = "TITULO"
        val col3 = "DESCRIPCION"
        val col4 = "PRECIO"

        //verificando si la bd posee una instancia ya declarada
        private var instance: ItemDAO? = null

        fun getInstance(context: Context): ItemDAO? = if (instance == null) {
            instance = ItemDAO(context)
            instance
        } else {
            instance
        }
    }

    //Creación de la tabla
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tableName(" +
                "$col1 INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$col2 TEXT NOT NULL," +
                "$col3 TEXT NOT NULL," +
                "$col4 REAL NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $dbName")
        onCreate(db)
    }

    fun insertData(item: ItemLoading) {
        return try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(col2,item.title)
            contentValues.put(col3,item.desc)
            contentValues.put(col4,item.price)
            db.insert(tableName,null,contentValues)
            db.close()
            toast("Nuevo Dato Ingresado ")
        }catch (e: Exception){
            toast("Dato No Ingresado ")
        }
    }

    fun updateData(item: ItemLoading,id: String){
        return  try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(col1,id)
            contentValues.put(col2,item.title)
            contentValues.put(col2,item.desc)
            contentValues.put(col2,item.price)
            db.update(tableName,contentValues,"ID = ?", arrayOf(id))
            db.close()
            toast("Dato Modificado")
        }catch (e: Exception){
            toast("Dato No Modificado")
        }
    }

    fun deleteData(id: String) {
        return  try {
            val db = writableDatabase
            db.delete(tableName,"ID = ?", arrayOf(id))
            db.close()
            toast("Dato Eliminado")
        }catch (e: Exception){
            toast("Dato No Eliminado")
        }

    }

    fun allData(): List<ItemLoading> {
        val items= mutableListOf<ItemLoading>()
        try {
            val db = readableDatabase
            val cursor =  db.rawQuery("SELECT * FROM $tableName",null)
            while (cursor.moveToNext()){
                var item: ItemLoading = ItemLoading(cursor.getString(1),cursor.getString(2),cursor.getDouble(3))
                items.add(item)
            }
            toast("Se pudo extraer datos")
        }catch (e: Exception){
            toast("No se pudo regresar los datos")
        }
        return items
    }

    val Context.database: ItemDAO?
        get() = getInstance(applicationContext)

    fun toast(mensaje: String) {
        Toast.makeText(context,mensaje, Toast.LENGTH_LONG).show()
    }
}