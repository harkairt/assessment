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
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  function main() {
    println('hello');
  }
  _.main = main;
  main();
  Kotlin.defineModule('numberphraserjs', _);
  return _;
}));

//# sourceMappingURL=numberphraserjs.js.map
