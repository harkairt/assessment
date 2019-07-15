class NumberPhraser {
    private val scaleNotations = listOf("", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion",
        "sextillion", "septillion", "octillion", "nonillion", "decillion", "undecillion", "duodecillion", "tredecillion")

    fun phraseNumber(number: String): String {
        if (number == "0")
            return "zero"

        val triplets = splitIntoTriplets(number)

        return triplets.concatPhrase()
    }

    private fun splitIntoTriplets(number: String): List<Triplet> {
        val triplets = number.reverseChunked(3).map { Triplet(it) }
        triplets.reversed().forEachIndexed { index, triplet ->
            triplet.scaleNotation = scaleNotations[index]
        }
        return triplets
    }
}