package com.example.NetProjectBackend.controllers;

import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.service.EventService;
import com.example.NetProjectBackend.service.impl.EventServiceImpl;
import com.example.NetProjectBackend.service.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @PostMapping("/full")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createEventUsersDishes(@RequestBody Event event, EventMember eventMember, EventDish eventDish) {
        return ResponseEntity.ok(eventService.createEventUsersDishes(event, eventMember, eventDish));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public void createEvent(@RequestBody Event event) {
        eventService.createEvent(event);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('USER')")
    public void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @PutMapping("/decline")
    @PreAuthorize("hasAuthority('USER')")
    public void declineEvent(@RequestParam int id) {
        eventService.declineEvent(id);
    }

    @GetMapping()
    //@PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readEvents(
            @RequestParam boolean is_owner,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String sortedBy,
            @RequestParam int limit,
            @RequestParam int page) {
        int user_id = (((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId());
        return ResponseEntity.ok(eventService.readEventSearch(user_id, is_owner, title, sortedBy, limit, page));
    }

    @GetMapping("/id")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> readEventById(int id) {
        return ResponseEntity.ok(eventService.eventReadById(id));
    }

    @PostMapping("/member")
    @PreAuthorize("hasAuthority('USER')")
    public void createEventMember(@RequestBody EventMember eventMember) {
        eventService.createEventMember(eventMember);
    }

    @PutMapping("/member")
    @PreAuthorize("hasAuthority('USER')")
    public void updateEventMemberStatus(@RequestBody EventMember eventMember) {
        eventService.updateEventMember(eventMember);
    }

    @DeleteMapping("/member")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteEventMember(@RequestParam int user_id,
                                  @RequestParam int event_id) {
        eventService.deleteEventMember(user_id, event_id);
    }

    @GetMapping("/member")
    @PreAuthorize("hasAuthority('USER')")
    public List<UserEventDto> readUsersFromEvent(@RequestParam int event_id,
                                                 @RequestParam(required = false) String first_name,
                                                 @RequestParam int limit,
                                                 @RequestParam int page) {
        return eventService.readUsersFromEvent(event_id, first_name, limit, page);
    }

    @PostMapping("/dish")
    @PreAuthorize("hasAuthority('USER')")
    public void createEventDish(@RequestBody EventDish eventDish) {
        eventService.createEventDish(eventDish);
    }


    @DeleteMapping("/dish")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteEventDish(@RequestParam int user_id,
                                @RequestParam int event_id,
                                @RequestParam int dish_id) {
        eventService.deleteEventDish(user_id, event_id, dish_id);
    }

    @GetMapping("/dish")
    @PreAuthorize("hasAuthority('USER')")
    public List<Dish> readDishesFromEvent(@RequestParam int event_id,
                                          @RequestParam(required = false) String title,
                                          @RequestParam int limit,
                                          @RequestParam int page) {
        return eventService.readDishFromEvent(event_id, title, limit, page);
    }

    @PostMapping("/message")
    @PreAuthorize("hasAuthority('USER')")
    public void createMessage(@RequestBody Message message) {
        eventService.createMessage(message);
    }

    @PutMapping("/message")
    @PreAuthorize("hasAuthority('USER')")
    public void updateMessage(@RequestBody Message message) {
        eventService.updateMessage(message);
    }

    @DeleteMapping("/message")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteMessage(@RequestBody Message message) {
        eventService.deleteMessage(message);
    }

    @GetMapping("/message")
    @PreAuthorize("hasAuthority('USER')")
    public List<Message> readMessagesFromEvent(@RequestParam int event_id,
                                                 @RequestParam(required = false) String text,
                                                 @RequestParam int limit,
                                                 @RequestParam int page) {
        return eventService.readMessageFromEvent(event_id, text, limit, page);
    }


    @PostMapping("/ingredint")
    @PreAuthorize("hasAuthority('USER')")
    public void createEventIngredient(@RequestBody EventIngredient eventIngredient) {
        eventService.createEventIngredient(eventIngredient);
    }

    @GetMapping("/ingredient")
    @PreAuthorize("hasAuthority('USER')")
    public List<EventIngredientDto> readUserIngredientsFromEvent(@RequestParam int event_id,
                                                                 @RequestParam int user_id,
                                                                 @RequestParam int limit,
                                                                 @RequestParam int page) {
        return eventService.readUserIngredientsFromEvent(event_id, user_id, limit, page);
    }


}
