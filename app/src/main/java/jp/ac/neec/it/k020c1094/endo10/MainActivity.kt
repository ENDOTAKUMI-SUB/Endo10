package jp.ac.neec.it.k020c1094.endo10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val _from = arrayOf("name", "price")
    private val _to = intArrayOf(R.id.tvMenuNameRow, R.id.tvMenuPriceRow)
    private val _helper = DatabaseHelper(this@MainActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()

        val db = _helper.writableDatabase
        val sql = "SELECT * FROM shop"
        val cursor = db.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {

                var menu = mutableMapOf("name" to cursor.getString(0), "price" to cursor.getString(1))
                menuList.add(menu)

                cursor.moveToNext()
            }
        }
        val lvMenu = findViewById<ListView>(R.id.listView)
        val adapter =
            SimpleAdapter(this@MainActivity, menuList, R.layout.row, _from, _to)
        lvMenu.adapter = adapter



        findViewById<Button>(R.id.buttonConfig).setOnClickListener(this)
    }


    override fun onRestart() {
        super.onRestart()

        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()

        val db = _helper.writableDatabase
        val sql = "SELECT * FROM shop"
        val cursor = db.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {

                var menu = mutableMapOf("name" to cursor.getString(0), "price" to cursor.getString(1))
                menuList.add(menu)

                cursor.moveToNext()
            }
        }
        val lvMenu = findViewById<ListView>(R.id.listView)
        val adapter =
            SimpleAdapter(this@MainActivity, menuList, R.layout.row, _from, _to)
        lvMenu.adapter = adapter

    }





    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonConfig -> {
                val intent = Intent(this@MainActivity, ConfigActivity::class.java)
                startActivity(intent)
            }

        }
    }
}