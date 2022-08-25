package dev.yawkar.auxilium.repository.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
@Data
@Accessors(chain = true)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "requester_id")
    private long requesterId;
    @Column(name = "helper_id")
    private long helperId;
}
