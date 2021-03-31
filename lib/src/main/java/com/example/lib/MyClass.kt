package com.example.lib

import sun.applet.Main

class MainClass {
    companion object {
        val firstPerson = Person("Kirill", 77, "89000663614")
    }
}

class Person(
    var name: String,
    private val weight: Int,
    private val telephoneNumber: String
)
fun main(args: Array<String>){
    //val person = MainClass.firstPerson
    //val a = person.name
    val obj = SomeClass(AnotherClass("hello"))
    //printSmth(obj?.obj?.str)
    obj.obj?.str?.let{ printSmth(it)}
}

fun printSmth(string: String){
      print(string)
}

data class SomeClass(
    val obj: AnotherClass?
)
data class AnotherClass(
    val str: String?
)

