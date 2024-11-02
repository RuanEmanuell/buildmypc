package com.example.myapplication.model

class MontagemView(var id: Int, var modeloId: Int, var modeloCpu : String, var modeloGpu : String,
                   var modeloRam : Float, var modeloSsd : Float, var modeloValor: Float,
                   var clienteCpf: String,  var clienteNome: String,  var clienteEmail: String,
                   var clienteTelefone: String) {

    fun toStringModelo(): String {
        return "$modeloCpu / $modeloGpu / ${modeloRam}GB / ${modeloSsd}GB"
    }
}