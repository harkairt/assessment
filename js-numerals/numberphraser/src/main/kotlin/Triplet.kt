class Triplet(private val numberAsString: String, var scaleNotation: String = ""){
    val numericValue = numberAsString.toInt()

    fun phrase(): String {
        if (numericValue == 0)
            return ""

        val phrasedValue = TripletPhraser.phrase(numericValue)
        return "$phrasedValue $scaleNotation".trim()
    }
}