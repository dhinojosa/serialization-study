package com.evolutionnext.kryo;

import java.time.LocalDate;
import java.util.Objects;

public class Coach {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Coach() {
    }

    public Coach(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() { return birthDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(firstName, coach.firstName) &&
                Objects.equals(lastName, coach.lastName) &&
                Objects.equals(birthDate, coach.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coach{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", birthDate='").append(birthDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
