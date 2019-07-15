class TripletPhraser{
    companion object {
        private val singleDigits = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        private val teens = listOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
        private val tenMultitudes = listOf("", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")

        fun phrase(number: Int): String = when (number) {
            in 1 until 10 -> phraseSingleDigit(number)
            in 10 until 20 -> phraseTeens(number)
            in 20 until 100 -> phraseDoubleDigit(number)
            in 100 until 1000 -> phraseTripleDigit(number)
            else -> throw IllegalArgumentException("Can only phrase numbers between ]0, 999[, input was: $number")
        }

        private fun phraseSingleDigit(number: Int): String = singleDigits[number]

        private fun phraseTeens(number: Int): String = teens[number - 10]

        private fun phraseDoubleDigit(number: Int): String {
            val tenner = number.div(10)
            val remainder = number.rem(10)

            return if (remainder == 0)
                tenMultitudes[tenner]
            else "${tenMultitudes[tenner]}-${phraseSingleDigit(remainder)}"
        }

        private fun phraseTripleDigit(number: Int): String {
            val hundreds = number.div(100)
            val remainder = number.rem(100)

            return if (remainder == 0)
                "${phraseSingleDigit(hundreds)} hundred"
            else
                "${phraseSingleDigit(hundreds)} hundred and ${phrase(remainder)}"
        }
    }
}