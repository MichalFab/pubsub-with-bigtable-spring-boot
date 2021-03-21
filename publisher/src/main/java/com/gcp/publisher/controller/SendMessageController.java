package com.gcp.publisher.controller;


import com.gcp.publisher.PublisherApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SendMessageController {

    @Autowired
    private PublisherApplication.PubsubOutboundGateway messagingGateway;

    @PostMapping("/postMessage")
    public RedirectView postMessage(@RequestParam("message") String message) {
        this.messagingGateway.sendToPubsub(message);
        return new RedirectView("/");
    }

}
