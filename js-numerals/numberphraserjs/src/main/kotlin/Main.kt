fun main(){
    val numberPhraser = NumberPhraser()

    val numeralPresenter = NumberConverterPresenter(numberPhraser)
    val numeralView = NumberConverterView(numeralPresenter)

    numeralPresenter.attach(numeralView)
    numeralView.show()
}