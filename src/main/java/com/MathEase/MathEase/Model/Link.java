package com.MathEase.MathEase.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"link\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Link {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long linkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    private Topic topic;

    @Column(name = "link_title")
    private String linkTitle;

    @Column(name = "link_url")
    private String linkUrl;

}
