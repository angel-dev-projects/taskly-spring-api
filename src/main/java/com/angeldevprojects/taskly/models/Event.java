package com.angeldevprojects.taskly.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 30)
    private String title;

    @Size(max = 200)
    private String description;

    @Column(nullable = false)
    private String start_date;

    @Column(nullable = false)
    private String end_date;

    private Boolean allDay = false;

    private String backgroundColor = "#0000FF";

    private String borderColor = "#FF1414";

    private String textColor = "#FFFFFF";

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
