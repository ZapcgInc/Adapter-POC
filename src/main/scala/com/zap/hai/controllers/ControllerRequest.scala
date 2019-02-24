package com.zap.hai.controllers

case class ControllerRequest(params : Map[String,String], body : Option[String])