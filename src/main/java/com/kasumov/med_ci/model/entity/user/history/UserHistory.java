package com.kasumov.med_ci.model.entity.user.history;

import com.kasumov.med_ci.model.entity.user.User;
import com.kasumov.med_ci.model.entity.user.items.Contact;
import com.kasumov.med_ci.model.entity.user.items.IdentityDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * связь с юсером
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    /**
     * документы подтверждающие личность
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userHistory")
    private Set<IdentityDocument> identityDocuments;

    /**
     * контакты юзера
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userHistory")
    private Set<Contact> contacts;

    public UserHistory(User user) {
        this.user = user;
    }
}