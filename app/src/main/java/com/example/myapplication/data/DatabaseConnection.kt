package com.example.myapplication.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseConnection(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS CLIENTE (" +
                        "cpf TEXT PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "email TEXT, " +
                        "telefone TEXT)"
            )

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS COMPUTADOR (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "modelo TEXT NOT NULL, " +
                        "ram REAL NOT NULL, " +
                        "valor REAL NOT NULL, " +
                        "fk_cpf TEXT, " +
                        "FOREIGN KEY(fk_cpf) REFERENCES CLIENTE(cpf))"
            )
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Erro ao criar as tabelas: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS CLIENTE")
        db.execSQL("DROP TABLE IF EXISTS COMPUTADOR")
        onCreate(db)
    }
}
