package com.zap.hai.validators

import com.zap.hai.controllers.ControllerRequest

trait RequestValidator {


  def validate(request : ControllerRequest)


}
