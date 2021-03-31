package com.example.lib

interface Base{
    fun printSmth()
}
class BaseImpl(val x: Int): Base{
    override fun printSmth() {
        print(x)
    }
}
class Derived(b: Base): Base by b{

}
fun main(args: Array<String>){
    val b = BaseImpl(5)
}