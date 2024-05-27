package com.MathEase.MathEase.Service;

import com.MathEase.MathEase.Model.Link;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    // list all links
    public List<Link> getLinksByTopic (Topic topic) {
        return linkRepository.findByTopic(topic);
    }

    // get link by id
    public Link getLinkById (Long linkId) {
        return linkRepository.findByLinkId(linkId);
    }

}
