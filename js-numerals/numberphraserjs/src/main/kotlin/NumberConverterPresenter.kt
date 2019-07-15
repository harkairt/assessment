class NumberConverterPresenter(private val numberPhraser: NumberPhraser) : NumberConverterContract.Presenter {
    private lateinit var view: NumberConverterContract.View

    override fun attach(view: NumberConverterContract.View) {
        this.view = view
    }

    override fun phrasingRequested(numberAsString: String) {
        var writtenNumeral = numberPhraser.phraseNumber(numberAsString)
        view.printNumber(writtenNumeral)
    }
}