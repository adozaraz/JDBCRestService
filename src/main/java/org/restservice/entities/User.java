package org.restservice.entities;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID uuid;
    private String username;
    private String password;

    private Role role;

    public User() {
        this.uuid = UUID.randomUUID();
        this.username = null;
        this.password = null;
        this.role = new Role();
    }

    public User(String username, String password, Role role) {
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(UUID id, String username, String password, Role role) {
        this.uuid = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String id, String username, String password, Role role) {
        this.uuid = UUID.fromString(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public String getUUID() {
        return uuid.toString();
    }

    public void setUUID(UUID id) {
        this.uuid = id;
    }

    public void setUUID(String id) {
        this.uuid = UUID.fromString(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, password, role);
    }

    public static class Role {
        private final UUID uuid;

        private String roleName;

        public Role() {
            this.uuid = UUID.randomUUID();
            this.roleName = "user";
        }

        public Role(String roleName) {
            this.uuid = UUID.randomUUID();
            this.roleName = roleName;
        }

        public Role(UUID id, String roleName) {
            this.uuid = id;
            this.roleName = roleName;
        }

        public Role(String id, String roleName) {
            this.uuid = UUID.fromString(id);
            this.roleName = roleName;
        }

        public String getUUID() {
            return uuid.toString();
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Role role = (Role) o;
            return Objects.equals(uuid, role.uuid) && Objects.equals(roleName, role.roleName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid, roleName);
        }
    }
}
