package com.hopper.util

import org.apache.commons.lang.StringUtils
/**
  * Wrapper on Apache Commons String Utils.
  * TODO : There might be a library out there is scala which already does this.
  */
object BaseStringUtils
{
    def isBlank(str:String): Boolean = StringUtils.isBlank(str)

    def isNotBlank(str:String): Boolean =  !isBlank(str)
}
