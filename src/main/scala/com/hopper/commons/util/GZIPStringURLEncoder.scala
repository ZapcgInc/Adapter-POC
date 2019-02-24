package com.hopper.commons.util

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.nio.charset.StandardCharsets
import java.util.Base64
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import com.twitter.io.StreamIO
import com.twitter.util.StringEncoder

trait GZIPStringURLEncoder extends StringEncoder
{
    override def encode(bytes: Array[Byte]): String =
    {
        val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream
        val gzipOutputStream: GZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream)
        gzipOutputStream.write(bytes)

        gzipOutputStream.finish()

        byteArrayOutputStream.toByteArray

        Base64.getUrlEncoder
          .withoutPadding()
          .encodeToString(byteArrayOutputStream.toByteArray)
    }

    def encodeString(str: String) : String = encode(str.getBytes(StandardCharsets.UTF_8))

    override def decode(str: String): Array[Byte] =
    {

        val decoded: Array[Byte] = Base64.getUrlDecoder.decode(str)
        val baos = new ByteArrayOutputStream

        StreamIO.copy(new GZIPInputStream(new ByteArrayInputStream(decoded)), baos)

        baos.toByteArray
    }

    def decodeString(input:String) : String = new String(decode(input), "UTF-8")
}

object GZIPStringURLEncoder extends GZIPStringURLEncoder
{

}
