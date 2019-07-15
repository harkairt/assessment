class NumberPhraser {
    private val scaleNotations = listOf("", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion",
        "sextillion", "septillion", "octillion", "nonillion", "decillion", "undecillion", "duodecillion", "tredecillion")

    fun phraseNumber(number: String): String {
        if (number == "0")
            return "zero"

        val triplets = splitIntoTriplets(number)

        if (triplets.size > 1 && triplets.last().numericValue in 1 until 100)
            return phraseWithPrefixingLastNumberPartWithAnd(triplets)

        return triplets.concatPhrase()
    }

    private fun splitIntoTriplets(number: String): List<Triplet> {
        val triplets = number.reverseChunked(3).map { Triplet(it) }
        triplets.reversed().forEachIndexed { index, triplet ->
            triplet.scaleNotation = scaleNotations[index]
        }
        return triplets
    }

    private fun phraseWithPrefixingLastNumberPartWithAnd(numberPartList: List<Triplet>): String {
        val lastNumberPart = numberPartList.last()
        val numberPartsButLast = numberPartList.minus(lastNumberPart)
        val joined = numberPartsButLast.concatPhrase()
        return "$joined and ${lastNumberPart.phrase()}"
    }
}