package com.zhy.listen.bean;

import java.sql.Timestamp;

public class Response {

    private ErrorCode errorCode;
    
    private Timestamp responseTime;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "Response [errorCode=" + errorCode + ", responseTime=" + responseTime + "]";
    }

    public Response() {
        responseTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
        result = prime * result + ((responseTime == null) ? 0 : responseTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Response other = (Response) obj;
        if (errorCode != other.errorCode)
            return false;
        if (responseTime == null) {
            if (other.responseTime != null)
                return false;
        } else if (!responseTime.equals(other.responseTime))
            return false;
        return true;
    }
    
    
}
