package com.MathEase.MathEase.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"topic\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Column(name= "PicturePath")
    private String picturePath;
}
