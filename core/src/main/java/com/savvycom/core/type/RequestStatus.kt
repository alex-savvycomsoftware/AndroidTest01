package com.savvycom.core.type

enum class RequestStatus(val value: Int) {
    NO_INTERNET_CONNECTION(470), // define myself
    ERROR_PARSE(471), // define myself
    ERROR_CLIENT(472), // define myself
    UNAUTHORIZED(401),
    CANCEL_REQUEST(473), // define myself
    NO_PERMISSION(474), // define myself
    CLIENT_TIMEOUT(475), // define myself
    UN_SUCCESS(476), // define myself
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500),
    SUCCESS(200),
    NOT_FOUND(404),
    FORBIDDEN(403),
    BAD_GATEWAY(502),
    GATEWAY_TIMEOUT(504),
    SERVICE_UNAVAILABLE(503)
}