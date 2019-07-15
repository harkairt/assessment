import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TripletTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "NUMERAL",
            "20ZZ",
            "2 0"
        ]
    )
    fun `Should throw argument exception when input cannot be parsed to int`(input: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Triplet(input)
        }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "-1",
            "1000",
            "9001"
        ]
    )
    fun `Should throw argument exception when input is out of supported range`(input: String) {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Triplet(input)
        }
    }


    @ParameterizedTest
    @CsvSource(
        value = [
            "0",
            "40",
            "400",
            "999"
        ]
    )
    fun `Numeric value should be input as Integer`(input: String) {
        val numberPart = Triplet(input)

        assert(numberPart.numericValue == input.toInt())
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "40, bunnies, forty bunnies",
            "400, megazillion, four hundred megazillion",
            "600, powerLevel, six hundred powerLevel"
        ], delimiter = ','
    )
    fun `Should append notation when phrased out`(input: String, notation: String, expectedPhrase: String) {
        val numberPart = Triplet(input)
        numberPart.scaleNotation = notation

        assert(numberPart.phrase() == expectedPhrase)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "0, ",
            "0, megazillion",
            "000, powerLevel"
        ], delimiter = ','
    )
    fun `Should not phrase out zero`(input: String, notation: String?) {
        val numberPart = Triplet(input)
        numberPart.scaleNotation = notation ?: ""

        assert(numberPart.phrase() == "")
    }
}