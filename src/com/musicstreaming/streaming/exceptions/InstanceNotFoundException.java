package com.musicstreaming.streaming.exceptions;

public class InstanceNotFoundException extends InstanceException {

    public InstanceNotFoundException(Object key, String className) {
        super("Instance not found", key, className);
    }
   

}
