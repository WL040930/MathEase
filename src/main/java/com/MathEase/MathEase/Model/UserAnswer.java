package com.MathEase.MathEase.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user_answer\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private Long userAnswerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", referencedColumnName = "option_id")
    private Options option;

}
