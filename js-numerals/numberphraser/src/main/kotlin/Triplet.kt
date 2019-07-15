class Triplet(private val numberAsString: String, var scaleNotation: String = ""){
    val numericValue = numberAsString.toInt()

    init {
        if (numericValue !in 0..999)
            throw IllegalArgumentException("Input can only be ]0, 999[")
    }

    fun phrase(): String {
        if (numericValue == 0)
            return ""

        val phrasedValue = TripletPhraser.phrase(numericValue)
        return "$phrasedValue $scaleNotation".trim()
    }
}