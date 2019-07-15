import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ExtensionTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "3, 3",
            "37, 37",
            "378, 378",
            "3789, 3 789",
            "37895, 37 895",
            "378952, 378 952",
            "3789522, 3 789 522",
            "37895224, 37 895 224",
            "378952241, 378 952 241",
            "3789522410, 3 789 522 410",
            "37895224104, 37 895 224 104",
            "378952241045, 378 952 241 045"
        ], delimiter = ','
    )
    fun shouldSplit(input: String, expectedSplit: String) {
        val chunks = input.reverseChunked(3)
        val expectedSplit = expectedSplit.split(' ')

        assert(chunks.size == expectedSplit.size)
        chunks.forEachIndexed { index, s ->
            assert(s == expectedSplit[index]){
                "Expected: ${expectedSplit[index]}, found:${chunks[index]}"
            }
        }
    }

}