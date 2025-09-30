package jpa.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "terms")
public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int status = 1;

    public Terms() {
    }

    public Terms(int id, int number, int duration, int status) {
        this.id = id;
        this.number = number;
        this.duration = duration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terms terms = (Terms) o;
        return id == terms.id && number == terms.number && duration == terms.duration && status == terms.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, duration, status);
    }
}


