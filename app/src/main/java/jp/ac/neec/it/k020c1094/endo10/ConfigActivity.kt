package jp.ac.neec.it.k020c1094.endo10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ConfigActivity : AppCompatActivity(), View.OnClickListener {
    private val _helper = DatabaseHelper(this@ConfigActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        findViewById<Button>(R.id.buttonSave).setOnClickListener(this)
        findViewById<Button>(R.id.buttonShop).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val editTextItemName = findViewById<EditText>(R.id.editTextItemName)
        val editTextPrice = findViewById<EditText>(R.id.editTextPrice)

        when (view.id) {
            R.id.buttonSave -> {
                Log.d("-----------", "tap !!!")

                if (editTextItemName.text.toString().isEmpty() && editTextPrice.text.toString()
                        .isEmpty()
                ) { // 未入力
                    Log.d("-----------", "未入力 !!!")
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.toast_err2),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (editTextItemName.text.toString()
                        .isNotEmpty() && editTextPrice.text.toString()
                        .isEmpty()
                ) { // 削除


                    Log.d("-----------", "削除 !!!")
                    val db = _helper.writableDatabase
                    val sqlDelete = "DELETE FROM shop WHERE name = ?"
                    var stmt = db.compileStatement(sqlDelete)
                    stmt.bindString(1, editTextItemName.text.toString())
                    stmt.executeUpdateDelete()

                    editTextItemName.setText("")


                } else if (editTextItemName.text.toString()
                        .isNotEmpty() && editTextPrice.text.toString()
                        .isNotEmpty()
                ) // 保存
                {
                    Log.d("-----------", "保存 !!!")
                    val db = _helper.writableDatabase

                    val sqlDelete = "DELETE FROM shop WHERE name = ?"
                    var stmt = db.compileStatement(sqlDelete)
                    stmt.bindString(1, editTextItemName.text.toString())
                    stmt.executeUpdateDelete()


                    val sqlInsert = "INSERT INTO shop ( name, price) VALUES ( ?, ?)"
                    stmt = db.compileStatement(sqlInsert)

                    stmt = db.compileStatement(sqlInsert)
                    stmt.bindString(1, editTextItemName.text.toString())
                    stmt.bindString(2, editTextPrice.text.toString())
                    // インサートSQLの実行。
                    stmt.executeInsert()


                    val sql = "SELECT * FROM shop"

                    editTextItemName.setText("")
                    editTextPrice.setText("")


                    val cursor = db.rawQuery(sql, null)
                    if (cursor.count > 0) {
                        cursor.moveToFirst()
                        while (!cursor.isAfterLast) {
                            Log.d("-----------", cursor.getString(0))
                            Log.d("-----------", cursor.getString(1))
                            Log.d("-----------", "............")
                            cursor.moveToNext()
                        }
                    }
                }
            }
            R.id.buttonShop -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        // ヘルパーオブジェクトの解放。
        _helper.close()
        super.onDestroy()
    }

}