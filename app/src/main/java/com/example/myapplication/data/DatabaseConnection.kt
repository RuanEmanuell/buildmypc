package com.example.myapplication.data

import Montagem
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myapplication.model.Cliente
import com.example.myapplication.model.Computador
import com.example.myapplication.model.MontagemView

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
                        "valor REAL NOT NULL)"
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
            }

            db.insert("COMPUTADORES", null, contentValues)
        } catch (e: Exception){
            throw e
        }
    }

    fun findAllPcs(db: SQLiteDatabase) : List<Computador>{
        val cursor = db.rawQuery("SELECT * FROM COMPUTADORES", null)
        var computerList = mutableListOf<Computador>()

        while(cursor.moveToNext()) run {
            var id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            var cpu = cursor.getString(cursor.getColumnIndexOrThrow("cpu"))
            var gpu = cursor.getString(cursor.getColumnIndexOrThrow("gpu"))
            var ram = cursor.getFloat(cursor.getColumnIndexOrThrow("ram"))
            var ssd = cursor.getFloat(cursor.getColumnIndexOrThrow("ssd"))
            var valor = cursor.getFloat(cursor.getColumnIndexOrThrow("valor"))

            val computer = Computador(id, cpu, gpu, ram, ssd, valor)
            computerList.add(computer)
        }

        cursor.close()
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
        val cursor = db.rawQuery("SELECT * FROM CLIENTES", null)
        var userList = mutableListOf<Cliente>()

        while(cursor.moveToNext()) run {
            var cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            var name = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            var email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            var telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

            val user = Cliente(cpf, name, email, telefone)
            userList.add(user)
        }
        cursor.close()

        return userList
    }

    fun findAllBuilds(db: SQLiteDatabase) : List<MontagemView>{
        var cursor = db.rawQuery(
            "SELECT m.id, pc.id as modeloId, pc.cpu, pc.gpu, pc.ram, pc.ssd, pc.valor, " +
                    "c.cpf, c.nome, c.email, c.telefone " +
                    "FROM MONTAGENS m " +
                    "JOIN COMPUTADORES pc ON m.modeloId = pc.id " +
                    "JOIN CLIENTES c ON m.clienteCPF = c.cpf", null
        )

        var buildList = mutableListOf<MontagemView>()

        while (cursor.moveToNext()) run {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val modeloId = cursor.getInt(cursor.getColumnIndexOrThrow("modeloId"))
            val modeloCpu = cursor.getString(cursor.getColumnIndexOrThrow("cpu"))
            val modeloGpu = cursor.getString(cursor.getColumnIndexOrThrow("gpu"))
            val modeloRam = cursor.getFloat(cursor.getColumnIndexOrThrow("ram"))
            val modeloSsd = cursor.getFloat(cursor.getColumnIndexOrThrow("ssd"))
            val modeloValor = cursor.getFloat(cursor.getColumnIndexOrThrow("valor"))
            val clienteCpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            val clienteNome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val clienteEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val clienteTelefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

            val build = MontagemView( id, modeloId, modeloCpu, modeloGpu, modeloRam, modeloSsd,
                modeloValor, clienteCpf, clienteNome, clienteEmail, clienteTelefone
            )
            buildList.add(build)
        }

        cursor.close()
        return buildList
    }

    fun insertBuild(montagem: Montagem, db: SQLiteDatabase){
        try {
            val contentValues = ContentValues().apply {
                put("modeloId", montagem.modeloId)
                put("clienteCpf", montagem.clienteCpf)

                Log.e("a", montagem.modeloId.toString())
            }

            db.insert("MONTAGENS", null, contentValues)
        } catch (e: Exception){
            throw e
        }
    }

}
