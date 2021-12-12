package com.example.NetProjectBackend.dao.impl;
import com.example.NetProjectBackend.confuguration.query.EventQuery;
import com.example.NetProjectBackend.dao.EventDao;
import com.example.NetProjectBackend.models.*;
import com.example.NetProjectBackend.models.dto.EventIngredientDto;
import com.example.NetProjectBackend.models.dto.UserEventDto;
import com.example.NetProjectBackend.models.entity.Dish;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
@Slf4j
public class EventDaoImpl implements EventDao {

    private final JdbcTemplate jdbcTemplate;
    private final EventQuery q;

    private static Event mapEventRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event(
                rs.getInt("id"),
                rs.getTimestamp("creation_timestamp"),
                rs.getTimestamp("event_timestamp"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("image_id"),
                rs.getString("status"),
                rs.getDouble("location_lat"),
                rs.getDouble("location_lon")
        );
    }

    private static EventMember mapEventMemberRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventMember(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("event_id"),
                rs.getString("status"),
                rs.getBoolean("is_owner")
        );
    }

    private static EventDish mapEventDishRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventDish(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("event_id"),
                rs.getInt("dish_id"),
                rs.getInt("quantity")
        );
    }


    private static Dish mapDishRow(ResultSet rs, int rowNum) throws SQLException {
        return new Dish(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("receipt"),
                rs.getString("image_id"),
                rs.getBoolean("is_active"),
                rs.getInt("likes")
        );
    }

    private static UserEventDto mapUserEventDtoRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserEventDto(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("imageId")
        );
    }

    private static Message mapMessageRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("event_id"),
                rs.getString("text"),
                rs.getTimestamp("timestamp")
        );
    }

    private static EventIngredient mapEventIngredientRow(ResultSet rs, int rowNum) throws SQLException {
        return new EventIngredient(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("event_id"),
                rs.getInt("ingredient_id"),
                rs.getDouble("amount")
        );
    }

    private static EventIngredientDto mapEventIngredientDtoRow(ResultSet rs, int rowNum) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getInt("id"));
        ingredient.setTitle(rs.getString("title"));
        ingredient.setDescription(rs.getString("description"));
        ingredient.setCategory(rs.getString("category"));
        ingredient.setImageId(rs.getString("image_id"));
        ingredient.setActive(rs.getBoolean("is_active"));
        ingredient.setMeasurement(rs.getString("measurement"));
        return new EventIngredientDto(
                ingredient,
                rs.getDouble("amount")
        );
    }

    @Override
    public List<Event> createEvent(Event event) {
        return jdbcTemplate.query(q.getEventCreate(),
                EventDaoImpl::mapEventRow,
                event.getCreation_timestamp(),
                event.getEvent_timestamp(),
                event.getTitle(),
                event.getDescription(),
                event.getImage_id(),
                event.getStatus(),
                event.getLocation_lat(),
                event.getLocation_lon()
        );

    }

    @Override
    public void updateEvent(Event event) {
        jdbcTemplate.update(q.getEventUpdate(),
                event.getEvent_timestamp(),
                event.getTitle(),
                event.getDescription(),
                event.getImage_id(),
                event.getStatus(),
                event.getLocation_lat(),
                event.getLocation_lon(),
                event.getId()
        );
    }

    @Override
    public void declineEvent(int id) {
        jdbcTemplate.update(q.getEventDecline(),
                "DECLINE",
                id
        );
    }

    @Override
    public List<Event> readEventSearch(int user_id, boolean is_owner, String title, String sortedBy, int limit, int offset) {
        if (Objects.equals(sortedBy, "title")) {
            return jdbcTemplate.query(q.getEventSearchOrderByTitle(),
                    EventDaoImpl::mapEventRow,
                    user_id, is_owner, title, limit, offset);
        } else if (Objects.equals(sortedBy, "event_timestamp")) {
            return jdbcTemplate.query(q.getEventSearchOrderByDate(),
                    EventDaoImpl::mapEventRow,
                    user_id, is_owner, title, limit, offset);
        }
        return jdbcTemplate.query(q.getEventSelectPageOwmMember(),
                EventDaoImpl::mapEventRow,
                user_id, is_owner, title, limit, offset);
    }

    @Override
    public Event readById(int id) {
        return (Event) jdbcTemplate.query(q.getEventSelectById(),
                EventDaoImpl::mapEventRow, id);
    }

    @Override
    public void createEventMember(EventMember eventMember) {
        jdbcTemplate.query(q.getEventMemberCreate(),
                EventDaoImpl::mapEventMemberRow,
                eventMember.getUser_id(),
                eventMember.getEvent_id(),
                eventMember.getStatus(),
                eventMember.getUser_id()
        );
    }

    @Override
    public void updateEventMember(String status, int user_id, int event_id) {
        jdbcTemplate.update(q.getEventMemberUpdate(),
                status, user_id, event_id
        );
    }

    @Override
    public void deleteEventMember(int user_id, int event_id) {
        jdbcTemplate.query(q.getEventMemberDelete(),
                EventDaoImpl::mapEventMemberRow,
                user_id, event_id
        );
    }

    @Override
    public List<UserEventDto> readUsersFromEvent(int event_id, String first_name, int limit, int offset) {
        return jdbcTemplate.query(q.getMembersOfEvent(),
                EventDaoImpl::mapUserEventDtoRow,
                event_id, first_name, limit, offset);
    }

    @Override
    public void createEventDish(EventDish eventDish) {
        jdbcTemplate.query(q.getEventDishCreate(),
                EventDaoImpl::mapEventDishRow,
                eventDish.getUser_id(),
                eventDish.getEvent_id(),
                eventDish.getDish_id(),
                eventDish.getQuantity()
        );
    }

    @Override
    public void deleteEventDish(int user_id, int event_id, int dish_id) {
        jdbcTemplate.query(q.getEventDishDelete(),
                EventDaoImpl::mapEventDishRow,
                user_id, event_id, dish_id
        );
    }

    @Override
    public List<Dish> readDishFromEvent(int event_id, String title, int limit, int offset) {
        return jdbcTemplate.query(q.getDishesOfEvent(),
                EventDaoImpl::mapDishRow,
                event_id, title, limit, offset);
    }

    @Override
    public void createMessage(Message message) {
        jdbcTemplate.query(q.getMessageCreate(),
                EventDaoImpl::mapMessageRow,
                message.getUser_id(),
                message.getEvent_id(),
                message.getText(),
                message.getTimestamp()
        );
    }

    @Override
    public void updateMessage(Message message) {
        jdbcTemplate.update(q.getMessageUpdate(),
                message.getText(),
                message.getUser_id(),
                message.getEvent_id(),
                message.getId()
        );
    }

    @Override
    public void deleteMessage(Message message) {
        jdbcTemplate.query(q.getMessageDelete(),
                EventDaoImpl::mapMessageRow,
                message.getUser_id(),
                message.getEvent_id(),
                message.getId()
        );
    }

    @Override
    public List<Message> readMessageFromEvent(int event_id, String text, int limit, int offset) {
        return jdbcTemplate.query(q.getMessagesOfEvent(),
                EventDaoImpl::mapMessageRow,
                event_id, text, limit, offset);
    }

    @Override
    public void createEventIngredient(EventIngredient eventIngredient) {
        jdbcTemplate.query(q.getEventIngredientCreate(),
                EventDaoImpl::mapEventIngredientRow,
                eventIngredient.getUser_id(),
                eventIngredient.getEvent_id(),
                eventIngredient.getIngredient_id(),
                eventIngredient.getAmount()
        );
    }

    @Override
    public List<EventIngredientDto> readUserIngredientsFromEvent(int user_id, int event_id, int limit, int offset) {
        return jdbcTemplate.query(q.getUserIngredientsOfEvent(),
                EventDaoImpl::mapEventIngredientDtoRow,
                user_id, event_id, limit, offset);
    }
}
