package com.evolutionnext.kryo;

import java.time.LocalDate;
import java.util.Objects;

public class Coach {
    private String firstName;
    private String lastName;
    private LocalDate localDate;

    public Coach(String firstName, String lastName, LocalDate localDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.localDate = localDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(firstName, coach.firstName) &&
                Objects.equals(lastName, coach.lastName) &&
                Objects.equals(localDate, coach.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, localDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coach{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", localDate=").append(localDate);
        sb.append('}');
        return sb.toString();
    }
}
