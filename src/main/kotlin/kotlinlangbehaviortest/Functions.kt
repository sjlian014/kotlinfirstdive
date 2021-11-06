package kotlinlangbehaviortest

// reference:
// official documentation: https://kotlinlang.org/docs/functions.html

fun main() {
    // basic function call syntax
    println(basicFunction("", null, arrayOf(1.0, 2.0), SomeObject(1), SomeObject(2)))

    // kotlin also supports named arguments in function calls, which allow you to label and  mix up the order of
    // the arguments. omitted arguments will use their default values defined in the function
    greet(target = "maven")

    unitFunction()

    println("result from singleExpressionFunction(10): ${singleExpressionFunction(10)}")

    // to use array with vararg, use the spread operator *
    println(basicFunction("", null, arrayOf(1.0, 2.0), *arrayOf(SomeObject(1), SomeObject(2))))
    // If you want to pass a primitive type array into vararg, you need to convert it to a regular (typed) array using
    // the toTypedArray() function

    SomeObject(0).memberFunction()

    SomeObject(1).extensionFunction()

    // infix function call syntax
    SomeObject(2) infixFunction SomeObject(3)

    outterFunction(10)

    // calling generic functions
    for(los in genericFunction<String>("los")) print("$los ")
    println("!")

    println(findFixPoint())
}

// format of a function:
//keyword|name  |param1|type |param2|type |param3|type         |var len param|type      |return type|function body
fun basicFunction(arg0: String, arg1: Int?, arg2: Array<Double>, vararg args: SomeObject): String {
    /* do something here*/

    // return statement
    return "basicFunction called"
}

// kotlin supports default argument:
fun greet(greeting: String = "hello", target: String = "jvm") {
    // there are some rules attached to functions with default arguments:
    //
    // 1. Overriding methods always use the same default parameter values as the base method. When overriding a method
    // that has default parameter values, the default parameter values must be omitted from the signature.
    // 2. If a default parameter precedes a parameter with no default value, the default value can only be used by
    // calling the function with named arguments
    // 3. If the last argument after default parameters is a lambda, you can pass it either as a named argument or
    // outside the parentheses
    println("$greeting $target")
}

// functions with return type Unit returns nothing
// functions with return type omitted is assumed to have return type of Unit
fun unitFunction(): Unit {
    println("unitFunction called")
    // return statement is optional
    return

    println("this part is unreachable") // in java this won't even compile
}

// single-expression functions have their logic condensed to an expression
// return type is optional can be inferred by compiler
fun singleExpressionFunction(number: Int) = number.toString()

class SomeObject(id: Int) {
    // function can also belong to an object
    fun memberFunction() = println("SomeObject's memberFunction called")
}

// extension functions extents the functionality of an object without using inheritance
fun SomeObject.extensionFunction() = println("SomeObject's extensionFunction called")

// infix functions allow a special way of function call (see main())
infix fun SomeObject.infixFunction(someOtherObject: SomeObject) {
    // there are some rules attached to infix functions
    //
    // 1. They must be member functions or extension functions (i.e. has to attach to an instance of an object).
    // 2. They must have a single parameter, that
    //      I. must not accept variable number of arguments
    //      II. must have no default value.

    println("infix function called")
}

// nested function and closure are also supported
fun outterFunction(it : Int) {
    var someVariable = 0
    fun localFunction(it : Int) {
        someVariable += it
        println(someVariable)
    }

    localFunction(it)
}

// generic function is supported as well
fun <T> genericFunction(item: T): List<T> = listOf(item, item, item)

// lastly, there is also tail recursion from functional programming
// (example copied from official documentation
val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double =
    if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))
// To be eligible for the tailrec modifier, a function must call itself as the last operation it performs. You cannot
// use tail recursion when there is more code after the recursive call, and you cannot use it
// within try/ catch/ finally blocks