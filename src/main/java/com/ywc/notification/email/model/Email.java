package com.ywc.notification.email.model;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.ywc.notification.email.utils.StringUtils;

import lombok.NonNull;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public interface Email extends Serializable {

    @NonNull
    InternetAddress getFrom();

    InternetAddress getReplyTo();

    @NonNull
    Collection<InternetAddress> getTo();

    @NonNull
    default Collection<InternetAddress> getCc() {
        return ImmutableList.of();
    }

    @NonNull
    default Collection<InternetAddress> getBcc() {
        return ImmutableList.of();
    }

    @NonNull
    default String getSubject() {
        return StringUtils.EMPTY;
    }

    @NonNull
    default String getBody() {
        return StringUtils.EMPTY;
    }

    @NonNull
    default Collection<EmailAttachment> getAttachments() {
        return ImmutableList.of();
    }

    /**
     * Return the charset encoding. Default value is UTF-8
     */
    //Observe that Charset does not guarantee that the object is Serializable, therefore we may break serialization
    String getEncoding();

    Locale getLocale();

    Date getSentAt();

    void setSentAt(Date sentAt);

    InternetAddress getReceiptTo();

    InternetAddress getDepositionNotificationTo();

    @NonNull
    default Map<String, String> getCustomHeaders() {
        return ImmutableMap.of();
    }

}