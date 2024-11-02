package com.example.myapplication.model

class Computador(var id: Int, var cpu: String, var gpu: String, var ram: Float, var ssd: Float, var valor: Float) {
    override fun toString(): String {
        return "$cpu / $gpu / ${ram}GB / ${ssd}GB"
    }
}