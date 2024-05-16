package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("""
            SELECT c
            FROM Contact c
                JOIN c.userHistory uh
                JOIN uh.user us
            WHERE us.id = :doctorId
                AND c.contactType IN :types
            """)
    List<Contact> getContactsByTypeAndDoctor(Set<ContactType> types, long doctorId);

    @Query("""
            SELECT c
            FROM Contact c
                JOIN c.userHistory uh
                JOIN uh.user us
            WHERE us.id = :userId
                AND c.contactType IN :types
            """)
    List<Contact> getContactsByUserId(Set<ContactType> types, long userId);

    @Query("""
            SELECT c
            FROM Contact c
            WHERE c.value = :chatId
                AND c.contactType IN :types
            """)
    List<Contact> getContactsByChatId(Set<ContactType> types, String chatId);
}
