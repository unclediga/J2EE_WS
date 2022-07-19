package ru.unclediga.javabrains.jaxrs.service;

import ru.unclediga.javabrains.jaxrs.data.DatabaseClass;
import ru.unclediga.javabrains.jaxrs.model.Comment;
import ru.unclediga.javabrains.jaxrs.model.ErrorMessage;
import ru.unclediga.javabrains.jaxrs.model.Message;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MessageService {

    private final Map<Long, Message> messages = DatabaseClass.getMessages();

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessages(int year) {
        if (year > 0) {
            // i don't learn Date() :(
            return messages
                    .values()
                    .stream()
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
        return null;
    }

    public Message getMessage(long id) {
        return messages.get(id);
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message) {
        return messages.put(message.getId(), message);
    }

    public void removeMessage(long id) {
        messages.remove(id);
    }

    public List<Comment> getComments(long messageId) {
        Response response
                = Response
                .status(404)
                .entity(new ErrorMessage("Message not found",
                        404,
                        "http:\\\\localhost"))
                .build();

        final Message message = DatabaseClass.getMessages().get(messageId);
        if (message == null) {
            throw new WebApplicationException(response);
        }
        return message.getAllComments();
    }

    public Comment getComment(long messageId, int commentId) {
        Response.ResponseBuilder response = Response.status(404);
        final Message message = DatabaseClass.getMessages().get(messageId);
        if (message == null) {
            throw new WebApplicationException(response.entity(new ErrorMessage(
                    "Message not found",
                    404,
                    "http:\\\\localhost\\message")).build());
        }

        final Comment comment = message.getComments().get(commentId);
        if (comment == null) {
            throw new WebApplicationException(response.entity(new ErrorMessage(
                            "Comment not found",
                            404,
                            "http:\\\\localhost\\comments")).build());
        }
        return comment;
    }

    public Comment addComment(long id, Comment comment) {
        final Map<Integer, Comment> comments = messages.get(id).getComments();
        comment.setId(comments.size() + 1);
        comments.put((int) comment.getId(), comment);
        return comment;
    }

    public void removeComment(long id, int commentId) {
        final Map<Integer, Comment> comments = messages.get(id).getComments();
        comments.remove(commentId);
    }
}
