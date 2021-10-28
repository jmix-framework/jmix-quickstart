package com.company.jmixpm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "NOTIFICATION", indexes = {
        @Index(name = "IDX_NOTIFICATION_SENDER_ID", columnList = "SENDER_ID"),
        @Index(name = "IDX_NOTIFICATION_RECIPIENT_ID", columnList = "RECIPIENT_ID")
})
@Entity
public class Notification {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Lob
    @NotNull
    @Column(name = "TEXT", nullable = false)
    private String text;

    @JoinColumn(name = "SENDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @NotNull
    @JoinColumn(name = "RECIPIENT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User recipient;

    @Column(name = "IS_READ")
    private Boolean isRead;

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}