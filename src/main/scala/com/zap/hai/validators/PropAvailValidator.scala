package com.zap.hai.validators
import com.zap.hai.controllers.ControllerRequest
import com.zap.hai.eps.{ErrorField, ErrorMessage}

import scala.collection.mutable.ArrayBuffer

trait PropAvailValidator extends RequestValidator{

  override def validate(request: ControllerRequest): Unit = {

    val errors = new ArrayBuffer[ErrorMessage]()

    if(!request.params.contains("checkin")){
      //create ErrorMessage
      errors += ErrorMessage("type","message",List(ErrorField("","","")),Nil)
    }
    if(true){
      errors += ErrorMessage("type","message",List(ErrorField("","","")),Nil)
    }

    ???
  }

}

/*
{
  "type": "invalid_input",
  "message": "An invalid request was sent in, please check the nested errors for details.",
  "errors": [
    {
      "type": "checkin.invalid_date_format",
      "message": "Invalid checkin format. It must be formatted in ISO 8601 (YYYY-mm-dd) http://www.iso.org/iso/catalogue_detail?csnumber=40874.",
      "fields": [
        {
          "name": "checkin",
          "type": "querystring",
          "value": "2019-01-1"
        }
      ]
    }
  ]
}
 */
