package kotlinlangbehaviortest

// reference:
// official documentation: https://kotlinlang.org/docs/control-flow.html

fun main() {
    println(IfElseTester.ifAsExpression(true))
    IfElseTester.basicIfElse(1.5)

    WhenTester.whenAsIfElseSub()
    println(WhenTester.whenWithEnum(WhenTester.Companion.State.PROBABLY_FALSE))
    WhenTester.whenWithExpressions(101)
    WhenTester.whenWithTransformation("hello kotlin")

    ForTester.forWithIn()
    ForTester.forWithRanges()
    ForTester.forWithIndex()

    WhileTester.basicWhileLoop()
    WhileTester.basicDoWhileLoop()
}

class IfElseTester {
    companion object {
        // if in kotlin is an expression and returns value
        // this is also why there isn't ternary operator in kotlin
        fun ifAsExpression(isInKotlin : Boolean) : String = if(isInKotlin) "good stuff!" else "hmmmm..."

        // if can also be used to execute block code without returning anything
        fun basicIfElse(jdkVersion : Double) {
            val standardVersionNumber : Int
            if(jdkVersion in 1.01..1.91) standardVersionNumber = ((jdkVersion - 1) * 10).toInt()
            else standardVersionNumber = jdkVersion.toInt()

            if(standardVersionNumber in 12..17) println("jdk version $standardVersionNumber is recent")
            else if(standardVersionNumber in 8..10) println("jdk version $standardVersionNumber is dated")
            else if(standardVersionNumber in 1..7) println("jdk version $standardVersionNumber is ancient")
        }
    }
}

class WhenTester {
    companion object {
        enum class State {
            TRUE, FALSE, MAYBE_TRUE, PROBABLY_FALSE
        }

        const val X = 10
        const val Y = 15

        // only X is 10 is evaluated and printed
        fun whenAsIfElseSub() = when {
            X == 10 -> println("X is 10")
            Y == 15 -> println("Y is 15")
            // when when is used as an expression, else is required if the other cases are not exhaustive
            else -> println("I have no idea what is going on")
        }

        fun whenWithEnum(state: State): String = when (state) {
            State.TRUE -> "success!"
            State.FALSE -> "failure!"
            // defining common behavior with ,
            State.MAYBE_TRUE, State.PROBABLY_FALSE -> "crash and burn"
            // else is not needed since all cases are covered
        }

        fun whenWithExpressions(number: Int?) {
            fun getRandomNumber(): Int = (101..103).random()
            when (number) {
                in 10..20 -> println("$number is between 10 and 20")
                getRandomNumber() -> println("$number is a lucky number")
                !in 100..1000 -> println("$number is not between 100 and 1000")
                !is Int -> println("how...?")
                null -> println("nothing here")
                else -> println("$number confuses me")
            }
        }

        fun whenWithTransformation(msg: String) = when (val newMsg = msg.slice(0..4)) {
            "hello" -> println("hello")
            else -> println("goodbye")
        }
    }
}

class ForTester {
    companion object {
        val langs = arrayOf("kotlin(!)", "java(?)", "groovy(???)")

        fun forWithIn() {
            print("favorite languages: ")
            for(lang in langs) print("$lang ")
            println()

        }

        fun forWithRanges() {
            var result = 1
            for(i in 1..10) result *= i
            println("1x2x3...x9x10 = $result")
        }

        fun forWithIndex() {
            println("language ranking: ")
            for(index in langs.indices) println("\t$index) ${langs[index]}")
            println()
            println("same thing with withIndex library function: ")
            for((index, value) in langs.withIndex()) println("\t$index) $value")
            println()
        }
    }
}

class WhileTester {
    companion object {

        // behaves like what you expect
        fun basicWhileLoop() {
            var counter = 1

            while(counter < 10) counter *= 2
            println("final result: $counter")
        }

        fun basicDoWhileLoop() {
            do {
                // you can declare variables inside the loop and use them in termination condition
                val randomNum = (1..5).random()
                print(if(randomNum != 5) "miss " else "...\nhit!")
            } while (randomNum != 5)
        }
    }
}
