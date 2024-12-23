package ru.darf.diarycompose.core.ext

fun String.addArgs(vararg args: String) = if (args.isEmpty()) {
    this
} else {
    val argsString = args.joinToString(prefix = "?", separator = ",") { "$it={$it}" }
    "$this$argsString"
}

inline fun <reified T> String.addArgs(vararg args: Pair<String, T>) = if (args.isEmpty()) {
    this
} else {
    val argsString = args.joinToString(prefix = "?", separator = ",") { "${it.first}=${it.second}" }
    "$this$argsString"
}