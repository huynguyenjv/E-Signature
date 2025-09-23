package com.backend.userservice.entities;

import com.backend.userservice.enums.OrganizationRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrganizations {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organizations organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrganizationRole role = OrganizationRole.MEMBER;
}
