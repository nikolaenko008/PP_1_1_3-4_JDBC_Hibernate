package jm.task.core.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

@Table
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        // Сравниваем по id.
        // Если id ещё нет (null), то сравниваем по остальным полям, чтобы отличить новые объекты.
        if (getId() != null && user.getId() != null) {
            return Objects.equals(getId(), user.getId());
        }
        return Objects.equals(getName(), user.getName())
                && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getAge(), user.getAge());
    }

    @Override
    public int hashCode() {
        // Если есть id — сравниваем по нему. Иначе — по всем полям.
        if (getId() != null) {
            return Objects.hash(getId());
        }
        return Objects.hash(getName(), getLastName(), getAge());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
