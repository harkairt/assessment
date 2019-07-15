(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'numberphraserjs'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'numberphraserjs'.");
    }
    root.numberphraserjs = factory(typeof numberphraserjs === 'undefined' ? {} : numberphraserjs, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var chunked = Kotlin.kotlin.text.chunked_94bcnn$;
  var reversed = Kotlin.kotlin.collections.reversed_7wnvza$;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var throwCCE = Kotlin.throwCCE;
  var reversed_0 = Kotlin.kotlin.text.reversed_gw00vp$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var trim = Kotlin.kotlin.text.trim_gw00vp$;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var throwUPAE = Kotlin.throwUPAE;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var equals = Kotlin.equals;
  var Unit = Kotlin.kotlin.Unit;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var last = Kotlin.kotlin.collections.last_2p1efm$;
  var take = Kotlin.kotlin.text.take_6ic1pp$;
  var takeLast = Kotlin.kotlin.text.takeLast_6ic1pp$;
  var any = Kotlin.kotlin.text.any_gw00vp$;
  var minus = Kotlin.kotlin.collections.minus_2ws7j4$;
  var listOf = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var checkIndexOverflow = Kotlin.kotlin.collections.checkIndexOverflow_za3lpa$;
  var IllegalArgumentException_init = Kotlin.kotlin.IllegalArgumentException_init_pdl1vj$;
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  function reverseChunked($receiver, step) {
    if (step === void 0)
      step = 3;
    var result = ArrayList_init();
    var tmp$;
    var tmp$_0;
    tmp$_0 = chunked(reversed_0(Kotlin.isCharSequence(tmp$ = $receiver) ? tmp$ : throwCCE()).toString(), step).iterator();
    while (tmp$_0.hasNext()) {
      var element = tmp$_0.next();
      var tmp$_1;
      result.add_11rb$(reversed_0(Kotlin.isCharSequence(tmp$_1 = element) ? tmp$_1 : throwCCE()).toString());
    }
    return copyToArray(reversed(result));
  }
  function concatPhrase$lambda(it) {
    return it.phrase();
  }
  function concatPhrase($receiver) {
    var destination = ArrayList_init();
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      if (element.numericValue > 0)
        destination.add_11rb$(element);
    }
    var $receiver_0 = joinToString(destination, ' ', void 0, void 0, void 0, void 0, concatPhrase$lambda);
    var tmp$_0;
    return trim(Kotlin.isCharSequence(tmp$_0 = $receiver_0) ? tmp$_0 : throwCCE()).toString();
  }
  function main() {
    var numberPhraser = new NumberPhraser();
    var numeralPresenter = new NumberConverterPresenter(numberPhraser);
    var numeralView = new NumberConverterView(numeralPresenter);
    numeralPresenter.attach_dtlqne$(numeralView);
    numeralView.show();
  }
  function NumberConverterContract() {
  }
  function NumberConverterContract$View() {
  }
  NumberConverterContract$View.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'View',
    interfaces: []
  };
  function NumberConverterContract$Presenter() {
  }
  NumberConverterContract$Presenter.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Presenter',
    interfaces: []
  };
  NumberConverterContract.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'NumberConverterContract',
    interfaces: []
  };
  function NumberConverterPresenter(numberPhraser) {
    this.numberPhraser_0 = numberPhraser;
    this.view_do0o54$_0 = this.view_do0o54$_0;
  }
  Object.defineProperty(NumberConverterPresenter.prototype, 'view_0', {
    get: function () {
      if (this.view_do0o54$_0 == null)
        return throwUPAE('view');
      return this.view_do0o54$_0;
    },
    set: function (view) {
      this.view_do0o54$_0 = view;
    }
  });
  NumberConverterPresenter.prototype.attach_dtlqne$ = function (view) {
    this.view_0 = view;
  };
  NumberConverterPresenter.prototype.phrasingRequested_61zpoe$ = function (numberAsString) {
    var writtenNumeral = this.numberPhraser_0.phraseNumber_61zpoe$(numberAsString);
    this.view_0.printNumber_61zpoe$(writtenNumeral);
  };
  NumberConverterPresenter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NumberConverterPresenter',
    interfaces: [NumberConverterContract$Presenter]
  };
  function NumberConverterView(presenter) {
    this.presenter_0 = presenter;
    var tmp$, tmp$_0;
    this.numeralInput_0 = Kotlin.isType(tmp$ = document.getElementById('numeralInput'), HTMLInputElement) ? tmp$ : throwCCE();
    this.phrasedOutput_0 = Kotlin.isType(tmp$_0 = document.getElementById('phrasedOutput'), HTMLParagraphElement) ? tmp$_0 : throwCCE();
  }
  NumberConverterView.prototype.printNumber_61zpoe$ = function (phrasedNumber) {
    this.phrasedOutput_0.innerText = phrasedNumber;
  };
  function NumberConverterView$show$lambda(this$NumberConverterView) {
    return function (event) {
      if (Kotlin.isType(event, KeyboardEvent) && equals('Enter', event.key)) {
        this$NumberConverterView.presenter_0.phrasingRequested_61zpoe$(this$NumberConverterView.numeralInput_0.value);
      }
      return Unit;
    };
  }
  NumberConverterView.prototype.show = function () {
    window.onkeydown = NumberConverterView$show$lambda(this);
  };
  NumberConverterView.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NumberConverterView',
    interfaces: [NumberConverterContract$View]
  };
  function NumberPhraser() {
    this.scaleNotations_0 = listOf(['', 'thousand', 'million', 'billion', 'trillion', 'quadrillion', 'quintillion', 'sextillion', 'septillion', 'octillion', 'nonillion', 'decillion', 'undecillion', 'duodecillion', 'tredecillion']);
  }
  NumberPhraser.prototype.phraseNumber_61zpoe$ = function (number) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    if (equals(number, '0'))
      return 'zero';
    if (number.length === 4) {
      tmp$ = toInt(number);
      tmp$_0 = (1100 <= tmp$ && tmp$ <= 2000);
    }
     else
      tmp$_0 = false;
    if (tmp$_0)
      return this.handleMultipleHundred_0(number);
    var triplets = this.splitIntoTriplets_0(number);
    if (triplets.size > 1) {
      tmp$_1 = last(triplets).numericValue;
      tmp$_2 = (1 <= tmp$_1 && tmp$_1 < 100);
    }
     else
      tmp$_2 = false;
    if (tmp$_2)
      return this.phraseWithPrefixingLastNumberPartWithAnd_0(triplets);
    return concatPhrase(triplets);
  };
  NumberPhraser.prototype.handleMultipleHundred_0 = function (number) {
    var hundredsPartPhrased = (new Triplet(take(number, 2), 'hundred')).phrase();
    var remainderPhrased = (new Triplet(takeLast(number, 2))).phrase();
    return any(remainderPhrased) ? hundredsPartPhrased + ' and ' + remainderPhrased : hundredsPartPhrased;
  };
  NumberPhraser.prototype.splitIntoTriplets_0 = function (number) {
    var $receiver = reverseChunked(number, 3);
    var destination = ArrayList_init_0($receiver.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== $receiver.length; ++tmp$) {
      var item = $receiver[tmp$];
      destination.add_11rb$(new Triplet(item));
    }
    var triplets = destination;
    var tmp$_0, tmp$_0_0;
    var index = 0;
    tmp$_0 = reversed(triplets).iterator();
    while (tmp$_0.hasNext()) {
      var item_0 = tmp$_0.next();
      item_0.scaleNotation = this.scaleNotations_0.get_za3lpa$(checkIndexOverflow((tmp$_0_0 = index, index = tmp$_0_0 + 1 | 0, tmp$_0_0)));
    }
    return triplets;
  };
  NumberPhraser.prototype.phraseWithPrefixingLastNumberPartWithAnd_0 = function (numberPartList) {
    var lastNumberPart = last(numberPartList);
    var numberPartsButLast = minus(numberPartList, lastNumberPart);
    var joined = concatPhrase(numberPartsButLast);
    return joined + ' and ' + lastNumberPart.phrase();
  };
  NumberPhraser.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NumberPhraser',
    interfaces: []
  };
  function Triplet(numberAsString, scaleNotation) {
    if (scaleNotation === void 0)
      scaleNotation = '';
    this.numberAsString_0 = numberAsString;
    this.scaleNotation = scaleNotation;
    this.numericValue = toInt(this.numberAsString_0);
    var tmp$;
    tmp$ = this.numericValue;
    if (!(0 <= tmp$ && tmp$ <= 999))
      throw IllegalArgumentException_init('Input can only be ]0, 999[');
  }
  Triplet.prototype.phrase = function () {
    if (this.numericValue === 0)
      return '';
    var phrasedValue = TripletPhraser$Companion_getInstance().phrase_za3lpa$(this.numericValue);
    var $receiver = phrasedValue + ' ' + this.scaleNotation;
    var tmp$;
    return trim(Kotlin.isCharSequence(tmp$ = $receiver) ? tmp$ : throwCCE()).toString();
  };
  Triplet.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Triplet',
    interfaces: []
  };
  function TripletPhraser() {
    TripletPhraser$Companion_getInstance();
  }
  function TripletPhraser$Companion() {
    TripletPhraser$Companion_instance = this;
    this.singleDigits_0 = listOf(['', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']);
    this.teens_0 = listOf(['ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen']);
    this.tenMultitudes_0 = listOf(['', 'ten', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety']);
  }
  TripletPhraser$Companion.prototype.phrase_za3lpa$ = function (number) {
    if (1 <= number && number < 10)
      return this.phraseSingleDigit_0(number);
    else if (10 <= number && number < 20)
      return this.phraseTeens_0(number);
    else if (20 <= number && number < 100)
      return this.phraseDoubleDigit_0(number);
    else if (100 <= number && number < 1000)
      return this.phraseTripleDigit_0(number);
    else
      throw IllegalArgumentException_init('Can only phrase numbers between ]0, 999[, input was: ' + number);
  };
  TripletPhraser$Companion.prototype.phraseSingleDigit_0 = function (number) {
    return this.singleDigits_0.get_za3lpa$(number);
  };
  TripletPhraser$Companion.prototype.phraseTeens_0 = function (number) {
    return this.teens_0.get_za3lpa$(number - 10 | 0);
  };
  TripletPhraser$Companion.prototype.phraseDoubleDigit_0 = function (number) {
    var tenner = number / 10 | 0;
    var remainder = number % 10;
    return remainder === 0 ? this.tenMultitudes_0.get_za3lpa$(tenner) : this.tenMultitudes_0.get_za3lpa$(tenner) + '-' + this.phraseSingleDigit_0(remainder);
  };
  TripletPhraser$Companion.prototype.phraseTripleDigit_0 = function (number) {
    var hundreds = number / 100 | 0;
    var remainder = number % 100;
    return remainder === 0 ? this.phraseSingleDigit_0(hundreds) + ' hundred' : this.phraseSingleDigit_0(hundreds) + ' hundred and ' + this.phrase_za3lpa$(remainder);
  };
  TripletPhraser$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var TripletPhraser$Companion_instance = null;
  function TripletPhraser$Companion_getInstance() {
    if (TripletPhraser$Companion_instance === null) {
      new TripletPhraser$Companion();
    }
    return TripletPhraser$Companion_instance;
  }
  TripletPhraser.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TripletPhraser',
    interfaces: []
  };
  _.reverseChunked_6ic1pp$ = reverseChunked;
  _.concatPhrase_o4bai4$ = concatPhrase;
  _.main = main;
  NumberConverterContract.View = NumberConverterContract$View;
  NumberConverterContract.Presenter = NumberConverterContract$Presenter;
  _.NumberConverterContract = NumberConverterContract;
  _.NumberConverterPresenter = NumberConverterPresenter;
  _.NumberConverterView = NumberConverterView;
  _.NumberPhraser = NumberPhraser;
  _.Triplet = Triplet;
  Object.defineProperty(TripletPhraser, 'Companion', {
    get: TripletPhraser$Companion_getInstance
  });
  _.TripletPhraser = TripletPhraser;
  main();
  Kotlin.defineModule('numberphraserjs', _);
  return _;
}));

//# sourceMappingURL=numberphraserjs.js.map
