package com.weather.report.helper;

public class ResponseArgs {

    public Object args;
    public ResponseStatus responseStatus;
    public RequestType requestType;

    public ResponseArgs(Object args, ResponseStatus responseStatus, RequestType requestType) {
        this.args = args;
        this.responseStatus = responseStatus;
        this.requestType = requestType;
    }
}
