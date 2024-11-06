package com.stream.message.adapter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message_log")
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String message;
    private String channel;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageLog that = (MessageLog) o;

        if (! Objects.equals(id, that.id)) return false;
        if (! Objects.equals(recipient, that.recipient)) return false;
        if (! Objects.equals(message, that.message)) return false;
        if (! Objects.equals(channel, that.channel)) return false;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageLog{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", channel='" + channel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}