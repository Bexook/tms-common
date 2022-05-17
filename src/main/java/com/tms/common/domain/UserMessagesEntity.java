package com.tms.common.domain;


import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedNativeQueries(value = {
        @NamedNativeQuery(
                name = "findBySenderIdAndOrderBySendingTime",
                query = "SELECT * FROM user_messages um WHERE um.sender_id = :senderid ORDER BY um.sending_time ASC"
        ),
        @NamedNativeQuery(
                name = "findByReceiverIdAndOrderBySendingTime",
                query = "SELECT * FROM user_messages um WHERE um.receiver_id = :senderid ORDER BY um.receiver_time ASC"
        )
})

@Data
@Entity
@Table(name = "user_message", schema = "tms")
@SQLDelete(sql = "UPDATE user_messages SET delete = true", check = ResultCheckStyle.COUNT)
@SQLDeleteAll(sql = "UPDATE user_messages SET delete = true", check = ResultCheckStyle.COUNT)
public class UserMessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id",
            referencedColumnName = "id")
    private UserEntity senderId;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id",
            referencedColumnName = "id")
    private UserEntity receiverId;

    @Column(name = "sending_time")
    private LocalDateTime sendingTime;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "read_time")
    private LocalDateTime readTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    private void prePersist() {
        sendingTime = LocalDateTime.now();
    }

}
