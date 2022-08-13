package dev.yawkar.auxilium.repository.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "dialogs")
@Data
@Accessors(chain = true)
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long chat1Id;
    private long chat2Id;
}
