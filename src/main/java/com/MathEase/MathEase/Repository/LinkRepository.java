package com.MathEase.MathEase.Repository;

import com.MathEase.MathEase.Model.Topic;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.MathEase.MathEase.Model.Link;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long>{

    // Find all links by topic
    List<Link> findByTopic(Topic topic);

    // Find link by linkId
    Link findByLinkId(Long linkId);

}
