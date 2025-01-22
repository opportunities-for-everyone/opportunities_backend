package com.project.opportunities.repository;

import com.project.opportunities.domain.model.Role;
import com.project.opportunities.domain.model.TelegramChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {
    boolean existsByChatId(String chatId);

    @Query("SELECT t FROM TelegramChat t JOIN t.user u JOIN u.roles r WHERE r.roleName IN (:roles)")
    List<TelegramChat> findChatsByRoles(@Param("roles") List<Role.RoleName> roles);

}
