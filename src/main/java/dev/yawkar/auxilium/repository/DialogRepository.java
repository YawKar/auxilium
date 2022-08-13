package dev.yawkar.auxilium.repository;

import dev.yawkar.auxilium.repository.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query(value = "select d from Dialog d where d.chat1Id = :chatId or d.chat2Id = :chatId")
    Dialog findDialogByChatId(long chatId);
}
