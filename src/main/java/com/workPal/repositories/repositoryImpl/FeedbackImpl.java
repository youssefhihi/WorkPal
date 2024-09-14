package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Feedback;
import com.workPal.model.Member;
import com.workPal.model.Space;
import com.workPal.repositories.interfaces.FeedbackRepository;
import com.workPal.connectDB.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FeedbackImpl implements FeedbackRepository {

    private final Connection connection = DatabaseConnection.connect();

    @Override
    public Map<UUID, Feedback> getFeedbackForSpace(UUID spaceId) {
        Map<UUID, Feedback> feedbackMap = new HashMap<>();
        String query = """
            SELECT f.id, f.comment, f.member_id, f.space_id, m.name AS member_name, s.name AS space_name
            FROM feedbacks f
            JOIN members m ON f.member_id = m.id
            JOIN spaces s ON f.space_id = s.id
            WHERE f.space_id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, spaceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Feedback feedback = new Feedback();
                UUID feedbackId = UUID.fromString(rs.getString("id"));
                feedback.setId(feedbackId);
                feedback.setComment(rs.getString("comment"));

                Member member = new Member();
                member.setId(UUID.fromString(rs.getString("member_id")));
                member.setName(rs.getString("member_name"));

                Space space = new Space();
                space.setId(UUID.fromString(rs.getString("space_id")));
                space.setName(rs.getString("space_name"));

                feedback.setMember(member);
                feedback.setSpace(space);

                feedbackMap.put(feedbackId, feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackMap;
    }

    @Override
    public Map<UUID, Feedback> getFeedbackForMember(UUID memberId) {
        Map<UUID, Feedback> feedbackMap = new HashMap<>();
        String query = """
            SELECT f.*, s.* , m.name AS member_name , m.email
            FROM feedbacks f
            JOIN spaces s ON f.space_id = s.id
            JOIN members m ON f.member_id = m.id
            WHERE f.member_id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(UUID.fromString(rs.getString("id")));
                feedback.setComment(rs.getString("comment"));
                Member member = new Member();
                member.setId(UUID.fromString(rs.getString("member_id")));
                member.setName(rs.getString("member_name"));
                member.setEmail(rs.getString("email"));

                Space space = new Space();
                space.setId(UUID.fromString(rs.getString("space_id")));
                space.setName(rs.getString("space_name"));

                feedback.setMember(member);
                feedback.setSpace(space);

                feedbackMap.put(feedback.getId(), feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackMap;
    }

    @Override
    public boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO feedbacks (comment, member_id, space_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, feedback.getComment());
            stmt.setObject(2, feedback.getMember().getId());
            stmt.setObject(3, feedback.getSpace().getId());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeFeedback(UUID feedbackId) {
        String query = "DELETE FROM feedbacks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, feedbackId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
