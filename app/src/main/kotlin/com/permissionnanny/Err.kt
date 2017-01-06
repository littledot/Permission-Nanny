package com.permissionnanny

/**
 * Collection of error messages.
 */
object Err {

    val NO_ENTITY = "ENTITY_BODY is missing."
    val NO_SENDER_IDENTITY = "SENDER_IDENTITY is missing."
    val NO_REQUEST_PARAMS = "REQUEST_PARAMS is missing"
    val UNSUPPORTED_OPCODE = "Unsupported operation [%s]."
    val NO_PERMISSION_MANIFEST = "PERMISSION_MANIFEST is missing."
    val UNSUPPORTED_DEEP_LINK_TARGET = "Unsupported deep link target [%s]."
}
