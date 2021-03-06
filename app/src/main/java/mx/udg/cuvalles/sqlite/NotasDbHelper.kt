package mx.udg.cuvalles.sqlite

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View

class NotasDbHelper(context: Context?, name:String?, factory: SQLiteDatabase.CursorFactory?, version:Int): SQLiteOpenHelper(
    context, name, factory, version) {

    val NOMBRE_TABLA = "notas"
    val _ID= "id"
    val TITLE = "Titulo"
    val  CONTENT  ="contenido"

    var database:SQLiteDatabase?=null
    val QUERY_CREATE_TABLE = "CREATE TABLE $NOMBRE_TABLA"+
            "($_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$TITLE TEXT NOT NULL, " +
            "$CONTENT TEXT);"
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(QUERY_CREATE_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $NOMBRE_TABLA")
        onCreate(p0)

    }
    fun open(){
        database = this.writableDatabase
    }

    override fun close() {
        super.close()
        database!!.close()
    }
    //insertar datos en la tabla
    fun addNota(titulo:String, contenido:String){

        //clave valor
        val contentValues = ContentValues()
        contentValues.put(TITLE, titulo)
        contentValues.put(CONTENT, contenido)

        database!!.insert(NOMBRE_TABLA, null, contentValues)


    }

    fun getListNotas():ArrayList<Notas>{
        val listaNotas= ArrayList<Notas>()
        val selectAll = "SELECT * FROM $NOMBRE_TABLA"
        val cursor: Cursor = database!!.rawQuery(selectAll, null)
        if (cursor.moveToFirst()){
            do {
                val nota = Notas().apply{
                    id= cursor.getInt(cursor.getColumnIndex(_ID))
                    titulo= cursor.getString(cursor.getColumnIndex(TITLE))
                    contenido = cursor.getString(cursor.getColumnIndex(CONTENT))
                }
                listaNotas.add(nota)
            }while (cursor.moveToNext())
        }
        return listaNotas

    }
}