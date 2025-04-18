package com.approf.approf.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "date"})
)
public class ProfAvail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private String workingHoursCsv;


    @Transient
    public List<Integer> getWorkingHours() {
        if (workingHoursCsv == null || workingHoursCsv.isEmpty()) return new ArrayList<>();
        return Arrays.stream(workingHoursCsv.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Transient
    public void setWorkingHours(List<Integer> hours) {
        this.workingHoursCsv = hours.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}