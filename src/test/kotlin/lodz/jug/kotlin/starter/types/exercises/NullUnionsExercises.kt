package lodz.jug.kotlin.starter.types.exercises

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class NullUnionsExercises : StringSpec() {
    init {
        "kotlin native type system".config(enabled = false) {
            NullSafeStringJoiner.join("[", "hello", "]") shouldBe "[Hello]"
            NullSafeStringJoiner.join(null, "hello", null) shouldBe "Hello"
            NullSafeStringJoiner.join("[", null, "]") shouldBe "[]"
            NullSafeStringJoiner.join("[", "   HELLO   ", "]") shouldBe "[Hello]"
        }

        "custom union type".config(enabled = false) {
            val hello = NullUnion.of("  hello  ")
            val helloNull = NullUnion.of(null)

            hello.safeCall { it.trim() }.safeCall { it.toLowerCase().capitalize() }.unsafeGet() shouldBe "Hello"
            helloNull.safeCall { it.trim() }.elvis({ it.toLowerCase().capitalize() }, "empty") shouldBe "empty"
        }
    }
}

//Exercise 1
object NullSafeStringJoiner {
    fun join(prefix: String?, middle: String?, suffix: String?): String = TODO()
}

//Exercise 2

sealed class NullUnion {
    abstract fun safeCall(call: (String) -> String): NullUnion
    abstract fun elvis(call: (String) -> String, alternative: String): String
    abstract fun unsafeGet(): String

    companion object {
        fun of(input: String?): NullUnion = if (input == null) NullValue else NonNullValue(input)
    }
}


class NonNullValue(private val v: String) : NullUnion() {
    override fun unsafeGet(): String = TODO()  // EXERCISE

    override fun elvis(call: (String) -> String, alternative: String): String = TODO() // EXERCISE

    override fun safeCall(call: (String) -> String): NullUnion = TODO() // EXERCISE
    override fun toString(): String {
        return "NonNullValue(v='$v')"
    }

}

object NullValue : NullUnion() {
    override fun unsafeGet(): String = TODO() // EXERCISE

    override fun elvis(call: (String) -> String, alternative: String): String = TODO() // EXERCISE

    override fun safeCall(call: (String) -> String): NullUnion = TODO() // EXERCISE
    override fun toString(): String = "NullValue"

}

