package com.MathEase.MathEase.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"question_attachment\"")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionAttachment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "question_attachment_id")
    private Long questionAttachmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Questions question;

    @Column(name = "attachment_filename", nullable = false)
    private String attachmentFilename;
    
}
