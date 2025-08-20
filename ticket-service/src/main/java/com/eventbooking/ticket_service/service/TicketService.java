package com.eventbooking.ticket_service.service;

import com.eventbooking.ticket_service.model.Ticket;
import com.eventbooking.ticket_service.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public Ticket bookTicket(Ticket ticket) {
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setPaid(false);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUser(String userId) {
        return ticketRepository.findByUserId(userId);
    }
}
