package dev.yawkar.auxilium.repository.entity;

import dev.yawkar.auxilium.context.ContextType;
import lombok.Data;
import lombok.experimental.Accessors;

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
    private ContextType contextType;
}
