package com.example.alex.wheaterapp

import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class WebServiceCall {

    fun callApi(temp: String?): String {
        var result = ""
        val SOAP_ACTION = Utils.SOAP_NAMESPACE + Utils.METHOD_NAME
        val soapObject = SoapObject(Utils.SOAP_NAMESPACE, Utils.METHOD_NAME)


        soapObject.addProperty("Celsius", temp)

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.setOutputSoapObject(soapObject)
        envelope.dotNet = true

        val httpTransportSE = HttpTransportSE(Utils.SOAP_URL)

        try {
            httpTransportSE.call(SOAP_ACTION, envelope)
            val soapPrimitive = envelope.response
            result = soapPrimitive.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}