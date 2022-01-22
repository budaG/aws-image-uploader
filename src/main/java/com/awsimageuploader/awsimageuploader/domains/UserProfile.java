package com.awsimageuploader.awsimageuploader.domains;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "UserProfile")
@Table(name = "user_profiles",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "user_name_unique",
                columnNames = "userName"
        )
        }
)
public class UserProfile {
    @Id
    private String userProfileId;
    @Column(
            nullable = false
    )
    private String userName;
    private String userProfileImageLink;
}
