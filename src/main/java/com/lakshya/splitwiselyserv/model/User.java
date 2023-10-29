package com.lakshya.splitwiselyserv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity(name = "splitwisely_user")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseModel {
    @ManyToMany(mappedBy = "users")
    List<Group> groups;
    private String name;
    private String email;
    private String phoneNumber;
}
