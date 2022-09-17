package com.gusrinda.invoicegenerator.data

data class ModelInvoicePriceInfo(
    val subTotal :String = "",
    val discountTotal : String = "",
    val ongkosTotal : String = "",
    val isUsingVoucherMember : Boolean = false,
    val kodeVoucherMember : String = "",
    val nominalVoucherMember : String = "",
    val isUsingVoucherSuplier : Boolean = false,
    val kodeVoucherSuplier : String = "",
    val nominalVoucherSuplier : String = "",
    val invoiceTotal : String = "",
    val paymentTotal : String = "",
    val changeTotal : String = ""
)