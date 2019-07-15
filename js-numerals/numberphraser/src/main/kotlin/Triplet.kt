class Triplet(private val numberAsString: String){
    fun phrase(): String {
        return TripletPhraser.phrase(numberAsString.toInt())
    }
}