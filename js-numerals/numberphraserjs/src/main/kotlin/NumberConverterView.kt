import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLParagraphElement
import org.w3c.dom.events.KeyboardEvent
import kotlin.browser.document
import kotlin.browser.window

class NumberConverterView(private val presenter: NumberConverterContract.Presenter) : NumberConverterContract.View {

    private val numeralInput = document.getElementById("numeralInput") as HTMLInputElement
    private val phrasedOutput = document.getElementById("phrasedOutput") as HTMLParagraphElement
    private val numeralInputSubmitButton = document.getElementById("numeralInputSubmitButton") as HTMLButtonElement

    override fun printNumber(phrasedNumber: String) {
        phrasedOutput.innerText = phrasedNumber
    }

    fun show(){
        window.onkeydown = { event ->
            if (event is KeyboardEvent && "Enter" == event.key) {
                presenter.phrasingRequested(numeralInput.value)
            }
        }

        numeralInputSubmitButton.onclick = {event ->
            presenter.phrasingRequested(numeralInput.value)
        }
    }

}