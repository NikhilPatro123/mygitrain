package com.ywc.notification.email.model;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

public interface EmailAttachment extends Serializable {

    String getAttachmentName();

    byte[] getAttachmentData();

    MediaType getContentType() throws IOException;

    default ByteArrayResource getInputStream() {
        return new ByteArrayResource(getAttachmentData());
    }

}