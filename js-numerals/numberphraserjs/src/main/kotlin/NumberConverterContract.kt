interface NumberConverterContract {

    interface View {
        fun printNumber(numberString: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun phrasingRequested(numberAsString: String)
    }

}