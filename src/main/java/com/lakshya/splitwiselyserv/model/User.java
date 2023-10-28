package com.lakshya.splitwiselyserv.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name = "SPLITWISELY_USER")
public class User extends BaseModel {
    private String name;
    private String email;
    private String phone;
    @ManyToMany
    List<Group> groups;
}
