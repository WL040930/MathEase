package com.MathEase.MathEase.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"options\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Options {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions question;

    @Column(name = "option", nullable = false)
    private String option;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;
}
