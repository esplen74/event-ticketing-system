package com.eventbooking.ticket_service.controller;

import com.eventbooking.ticket_service.model.Ticket;
import com.eventbooking.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.bookTicket(ticket));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable String userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }
    @GetMapping
    public ResponseEntity<String> getTickets() {
        return ResponseEntity.ok("Ticket service is working!");
    }
}
