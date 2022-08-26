package dev.yawkar.auxilium.repository.entity;

import dev.yawkar.auxilium.context.ContextType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chats")
@Data
@Accessors(chain = true)
public class Chat {

    @Id
    private long id;
    @Column(name = "context_type")
    private ContextType contextType;
    @Column(name = "is_ready_to_help")
    private boolean readyToHelp;
    @Column(name = "session_id")
    private long sessionId;
}
