package com.evolutionnext.kryo;

import java.util.Objects;

public class Team {
    private String name;
    private Coach coach;
    private Stadium stadium;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, Coach coach, Stadium stadium) {
        this.name = name;
        this.coach = coach;
        this.stadium = stadium;
    }

    public String getName() {
        return name;
    }

    public Coach getCoach() {
        return coach;
    }

    public Stadium getStadium() {
        return stadium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Team{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
