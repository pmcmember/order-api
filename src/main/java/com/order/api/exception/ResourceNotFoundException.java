package com.order.api.exception;

/**
 * リソースが見つからない場合のエラー
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2128348422937977468L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
