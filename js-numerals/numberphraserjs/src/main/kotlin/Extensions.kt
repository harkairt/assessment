fun String.reverseChunked(step: Int = 3): Array<String> {
    val result = mutableListOf<String>()

    this.reversed().chunked(step).forEach { result.add(it.reversed()) }

    return result.reversed().toTypedArray()
}

fun List<Triplet>.concatPhrase(): String = this
    .filter { it.numericValue > 0 }
    .joinToString(" ") { it.phrase() }.trim()
