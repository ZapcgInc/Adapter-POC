package com.zap.hai.transformers

trait AgodaToEpsXfmr[I,O] {

  def transform(i : I) : O

}
