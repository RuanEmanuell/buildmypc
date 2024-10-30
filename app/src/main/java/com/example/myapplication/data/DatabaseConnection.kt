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
                "CREATE TABLE IF NOT EXISTS CLIENTES (" +
                        "cpf TEXT PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "email TEXT, " +
                        "telefone TEXT)"
            )

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS COMPUTADORES (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "cpu TEXT NOT NULL, " +
                        "gpu TEXT NOT NULL, " +
                        "ram REAL NOT NULL, " +
                        "ssd TEXT NOT NULL, " +
                        "valor REAL NOT NULL, " +
                        "clienteCpf TEXT, " +
                        "FOREIGN KEY(clienteCpf) REFERENCES CLIENTES(cpf))"
            )

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS MONTAGENS (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "modeloId INTEGER, " +
                        "clienteCpf TEXT, " +
                        "FOREIGN KEY(modeloId) REFERENCES COMPUTADORES(modeloId), " +
                        "FOREIGN KEY(clienteCpf) REFERENCES CLIENTES(cpf))"
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
