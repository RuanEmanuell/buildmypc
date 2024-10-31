package com.example.myapplication.data

import Montagem
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myapplication.model.Cliente
import com.example.myapplication.model.Computador

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
            Log.e("DatabaseError", "Erro ao criar as tabelas: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS CLIENTES")
        db.execSQL("DROP TABLE IF EXISTS COMPUTADORES")
        onCreate(db)
    }

    fun insertPC(computador: Computador, db: SQLiteDatabase){
        try {
            val contentValues = ContentValues().apply {
                put("cpu", computador.cpu)
                put("gpu", computador.gpu)
                put("ram", computador.ram)
                put("ssd", computador.ssd)
                put("valor", computador.valor)
                put("clienteCpf", computador.clienteCpf)
            }

            db.insert("COMPUTADORES", null, contentValues)
        } catch (e: Exception){
            throw e
        }
    }

    fun findAllPcs(db: SQLiteDatabase) : List<Computador>{
        val cursor = db.rawQuery("SELECT * FROM Computadores", null)
        var computerList = mutableListOf<Computador>()

        while(cursor.moveToNext()) run {
            var id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            var cpu = cursor.getString(cursor.getColumnIndexOrThrow("cpu"))
            var gpu = cursor.getString(cursor.getColumnIndexOrThrow("gpu"))
            var ram = cursor.getFloat(cursor.getColumnIndexOrThrow("ram"))
            var ssd = cursor.getFloat(cursor.getColumnIndexOrThrow("ssd"))
            var valor = cursor.getFloat(cursor.getColumnIndexOrThrow("valor"))
            var clienteCpf = cursor.getString(cursor.getColumnIndexOrThrow("clienteCpf"))

            val computer = Computador(id, cpu, gpu, ram, ssd, valor, clienteCpf)
            computerList.add(computer)
        }

        return computerList
    }

    fun insertUser(cliente: Cliente, db: SQLiteDatabase){
        try {
            val contentValues = ContentValues().apply {
                put("cpf", cliente.cpf)
                put("nome", cliente.nome)
                put("email", cliente.email)
                put("telefone", cliente.telefone)
            }

            db.insert("CLIENTES", null, contentValues)
        } catch (e: Exception){
            throw e
        }
    }

    fun findAllUsers(db: SQLiteDatabase) : List<Cliente>{
        val cursor = db.rawQuery("SELECT * FROM Clientes", null)
        var userList = mutableListOf<Cliente>()

        while(cursor.moveToNext()) run {
            var cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            var name = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            var email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            var telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

            val user = Cliente(cpf, name, email, telefone)
            userList.add(user)
        }

        return userList
    }

    fun findAllBuilds(db: SQLiteDatabase) : List<Montagem>{
        var buildList = mutableListOf<Montagem>()
        return buildList
    }

    fun insertBuild(montagem: Montagem, db: SQLiteDatabase){
        try {
            val contentValues = ContentValues().apply {
                put("modeloId", montagem.modeloId)
                put("clienteCpf", montagem.clienteCpf)
            }

            db.insert("MONTAGENS", null, contentValues)
        } catch (e: Exception){
            throw e
        }
    }

}
