class NumberPhraser {
    fun phraseNumber(number: String): String {
        if (number == "0")
            return "zero"

        return Triplet(number).phrase()
    }
}