package com.al_najah.tatweer.object.mothers;

import com.al_najah.tatweer.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class UserObjectMother {
    public static class MarioUserValues {
        public static final Long USER_ID = 1L;
        public static final UUID USER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        public static final String USERNAME = "superMario";
        public static final String FIRST_NAME = "Mario";
        public static final String LAST_NAME = "Mario";
        public static final String EMAIL = "mario@mushroom.kingdom";
        public static final LocalDateTime CREATED_AT = LocalDateTime.parse("2022-01-15T10:30:00");
        public static final LocalDateTime UPDATED_AT = LocalDateTime.parse("2022-03-20T14:45:00");
    }
    public static User createMario() {
        return User.builder()
                .userId(MarioUserValues.USER_ID)
                .userUuid(MarioUserValues.USER_UUID)
                .username(MarioUserValues.USERNAME)
                .firstName(MarioUserValues.FIRST_NAME)
                .lastName(MarioUserValues.LAST_NAME)
                .email(MarioUserValues.EMAIL)
                .createdAt(MarioUserValues.CREATED_AT)
                .updatedAt(MarioUserValues.UPDATED_AT)
                .habits(new ArrayList<>())
                .build();
    }

    public static User createLink() {
        return User.builder()
                .userId(2L)
                .userUuid(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"))
                .username("heroOfTime")
                .firstName("Link")
                .lastName("Hero")
                .email("link@hyrule.com")
                .createdAt(LocalDateTime.parse("2022-02-28T09:15:00"))
                .updatedAt(LocalDateTime.parse("2022-04-10T11:30:00"))
                .habits(new ArrayList<>())
                .build();
    }
}
