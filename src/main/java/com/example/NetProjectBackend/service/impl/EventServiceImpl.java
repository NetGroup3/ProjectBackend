package com.example.NetProjectBackend.service.impl;

import com.example.NetProjectBackend.dao.EventDao;
import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;
import com.example.NetProjectBackend.service.EventService;
import com.example.NetProjectBackend.service.Paginator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    @Override
    public void createEvent(Event event) {
        eventDao.createEvent(event);
    }

    @Override
    public int createEventUsersDishes(Event event, EventMember eventMember, EventDish eventDish) {
        List<Event> newEvent = eventDao.createEvent(event);
        int eventId = newEvent.get(0).getId();
        eventMember.setEvent_id(eventId);
        eventDish.setEvent_id(eventId);
        eventDao.createEventMember(eventMember);
        eventDao.createEventDish(eventDish);
        return newEvent.get(0).getId();
    }

    @Override
    public void updateEvent(Event event) {
        eventDao.updateEvent(event);
    }

    @Override
    public void declineEvent(int id) {
        String status = "DECLINE";
        eventDao.declineEvent(id, status);
    }

    @Override
    public List<Event> readEventSearch(int user_id, boolean is_owner, String title, String sortedBy, int limit, int page) {
        if (title == null) title = "%";
        else title = "%" + title + "%";
        return eventDao.readEventSearch(user_id, is_owner, title, sortedBy, limit, limit*page);
    }

    @Override
    public Event eventReadById(int id) {
        return eventDao.readById(id).get(0);
    }

    @Override
    public void createEventMember(EventMember eventMember) {
        eventDao.createEventMember(eventMember);
    }

    @Override
    public void updateEventMember(EventMember eventMember) {
        eventDao.updateEventMember(eventMember.getStatus(), eventMember.getUser_id(), eventMember.getEvent_id());
    }

    @Override
    public void deleteEventMember(int user_id, int event_id) {
        eventDao.deleteEventMember(user_id, event_id);
    }

    @Override
    public List<UserEventDto> readUsersFromEvent(int event_id, String first_name, int limit, int page) {
        if (first_name == null) first_name = "%";
        else first_name = "%" + first_name + "%";
        return eventDao.readUsersFromEvent(event_id, first_name, limit, limit*page);
    }

    @Override
    public void createEventDish(EventDish eventDish) {
        eventDao.createEventDish(eventDish);
    }

    @Override
    public void deleteEventDish(int user_id, int event_id, int dish_id) {
        eventDao.deleteEventDish(user_id, event_id, dish_id);
    }

    @Override
    public List<Dish> readDishFromEvent(int event_id, String title, int limit, int page) {
        if (title == null) title = "%";
        else title = "%" + title + "%";
        eventDao.readDishFromEvent(event_id, title, limit, limit*page);
        return null;
    }

    @Override
    public void createMessage(Message message) {
        eventDao.createMessage(message);
    }

    @Override
    public void updateMessage(Message message) {
        eventDao.updateMessage(message);
    }

    @Override
    public void deleteMessage(Message message) {
        eventDao.deleteMessage(message);
    }

    @Override
    public List<Message> readMessageFromEvent(int event_id, String text, int limit, int page) {
        if (text == null) text = "%";
        else text = "%" + text + "%";
        return eventDao.readMessageFromEvent(event_id, text, limit, limit*page);
    }

    @Override
    public void createEventIngredient(EventIngredient eventIngredient) {
        eventDao.createEventIngredient(eventIngredient);
    }

    @Override
    public List<EventIngredientDto> readUserIngredientsFromEvent(int user_id, int event_id, int limit, int page) {
        return eventDao.readUserIngredientsFromEvent(user_id, event_id, limit, limit*page);
    }
}
