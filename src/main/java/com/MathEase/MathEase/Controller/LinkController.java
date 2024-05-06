package com.MathEase.MathEase.Controller;

import com.MathEase.MathEase.Model.Link;
import com.MathEase.MathEase.Model.LinkDTO;
import com.MathEase.MathEase.Model.Topic;
import com.MathEase.MathEase.Repository.LinkRepository;
import com.MathEase.MathEase.Service.LinkService;
import com.MathEase.MathEase.Service.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LinkController {

    @Autowired
    private final MenuController menuController;

    @Autowired
    private TopicService topicService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private LinkRepository linkRepository;

    public LinkController(MenuController menuController) {
        this.menuController = menuController;
    }

    @GetMapping("/admin/link")
    public String adminLink(HttpSession session, Model model) {

        if (session.getAttribute("userId") == null || !session.getAttribute("role").equals("admin")) {
            return "redirect:/login";
        }

        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        menuController.setMenuBar(session, model);

        return "admin/admin-link";
    }

    @GetMapping("/api/links/{topicId}")
    public ResponseEntity<List<Link>> getQuestionsByTopicId(@PathVariable Long topicId) {
        try {
            Topic topic = topicService.getTopicById(topicId);
            List<Link> links = linkService.getLinksByTopic(topic);
            return ResponseEntity.ok(links);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/addLinks")
    public ResponseEntity<String> addLinks(@RequestParam("topicId") Long topicId,
                                           @RequestParam("linkTitle") String linkTitle,
                                           @RequestParam("linkURL") String linkUrl){
        try {
            Link link = new Link();
            link.setLinkTitle(linkTitle);
            link.setLinkUrl(linkUrl);

            Topic topic = topicService.getTopicById(topicId);
            link.setTopic(topic);

            linkRepository.save(link);
            return ResponseEntity.ok("Link added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/getLinksInfo/{linkId}")
    public ResponseEntity<LinkDTO> getLinkInfo(@PathVariable Long linkId) {
        try {
            Link link = linkService.getLinkById(linkId);

            LinkDTO linkDTO = new LinkDTO();
            linkDTO.setLinkId(link.getLinkId());
            linkDTO.setLink(link.getLinkTitle());
            linkDTO.setUrl(link.getLinkUrl());

            return ResponseEntity.ok(linkDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/editLinks")
    public ResponseEntity<String> editLinks(@RequestParam("linkId") Long linkId,
                                            @RequestParam("linkTitle") String linkTitle,
                                            @RequestParam("linkURL") String linkUrl){
        try {
            Link link = linkService.getLinkById(linkId);
            link.setLinkTitle(linkTitle);
            link.setLinkUrl(linkUrl);

            linkRepository.save(link);
            return ResponseEntity.ok("Link updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/deleteLinks")
    public ResponseEntity<String> deleteLinks(@RequestParam("linkId") Long linkId){
        try {
            Link link = linkService.getLinkById(linkId);
            linkRepository.delete(link);
            return ResponseEntity.ok("Link deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
