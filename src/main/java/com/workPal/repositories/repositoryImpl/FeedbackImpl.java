package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Feedback;
import com.workPal.repositories.interfaces.FeedbackRepository;
import com.workPal.connectDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeedbackImpl implements FeedbackRepository {

    private final Connection connection = DatabaseConnection.connect();

    @Override
    public List<Feedback> getFeedbackForSpace(UUID spaceId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedbacks WHERE space_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, spaceId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(UUID.fromString(rs.getString("id")));
                feedback.setComment(rs.getString("comment"));
                feedback.setMemberId(UUID.fromString(rs.getString("member_id")));
                feedback.setSpaceId(UUID.fromString(rs.getString("space_id")));
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    @Override
    public List<Feedback> getFeedbackForMember(UUID memberId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedbacks WHERE member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(UUID.fromString(rs.getString("id")));
                feedback.setComment(rs.getString("comment"));
                feedback.setMemberId(UUID.fromString(rs.getString("member_id")));
                feedback.setSpaceId(UUID.fromString(rs.getString("space_id")));
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    @Override
    public boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO feedbacks (id, comment, member_id, space_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, feedback.getId());
            stmt.setString(2, feedback.getComment());
            stmt.setObject(3, feedback.getMemberId());
            stmt.setObject(4, feedback.getSpaceId());
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
