package com.workPal.repositories.repositoryImpl;

import com.workPal.enums.Role;
import com.workPal.model.Member;
import com.workPal.repositories.interfaces.MemberRepository;
import com.workPal.utility.UserUtility;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MemberImpl implements MemberRepository {
    private final Connection connection;

    public MemberImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(Member member) {
        try{
            String sql = "INSERT INTO members (name, email, password, phoneNumber,role) VALUES (?, ?, ?,?,?::user_role)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            String hashPassword = UserUtility.hashPassword(member.getPassword());
            statement.setString(3, hashPassword);
            statement.setString(4,member.getPhoneNumber());
            System.out.println("dss" + member.getPhoneNumber());
            statement.setString(5, Role.member.name());
            statement.executeUpdate();
            System.out.println("✅ Registration successful! You can now log in."); ;
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Error in saving member in memberImpl" + e.getMessage());
            System.out.println("❌Registration failed . please try again");
        }
    }
    @Override
    public void update(Member member){
        String passwordKey = member.getPassword().isEmpty() ? "" : ", password = ?";

        try{
            String sql = "UPDATE members SET name = ? , email = ?, phoneNumber = ?" + passwordKey + " WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhoneNumber());
            if (!passwordKey.isEmpty()) {
                String hashedPassword;
                try {
                    hashedPassword = UserUtility.hashPassword(member.getPassword());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("Error hashing the password: " + e.getMessage(), e);
                }
                statement.setString(4, hashedPassword);
                statement.setObject(5, member.getId());
            } else {
                statement.setObject(4, member.getId());
            }
            statement.executeUpdate();
            System.out.println("✅ Profile updated successfully!");
        } catch (Exception e) {
            System.out.println("Error in updating member in MemberImpl: " + e.getMessage());
        }

    }
    @Override
    public void delete (UUID id){
        try{
            String sql = "Delete FROM members WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Member> findMember(UUID id){
        try{
            String sql = "SELECT * FROM members WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                   Member member = new Member();
                    member.setId((UUID) rs.getObject("id"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setPhoneNumber(rs.getString("phoneNumber"));
                    return Optional.of(member);
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  Optional.empty();
    }

    @Override
    public Optional<Member> findMember(String  email){
        try{
            String sql = "SELECT * FROM members WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId((UUID) rs.getObject("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPhoneNumber(rs.getString("phoneNumber"));
                return Optional.of(member);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  Optional.empty();
    }


    @Override
    public Map<UUID, Member> getAll() {
            Map<UUID, Member> members = new HashMap<>();
        try{
            String sql = "SELECT * FROM members";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(UUID.fromString(rs.getString("id")));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setPhoneNumber(rs.getString("phoneNumber"));
                members.put(member.getId(),member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


    @Override
    public Map<UUID, Member> searchMembers(String query) {
        Map<UUID, Member> result = new HashMap<>();

        try{
            String sql = "SELECT * FROM members WHERE name ILIKE ? OR email ILIKE ? OR phoneNumber ILIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            statement.setString(3, "%" + query + "%");


            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Member member = new Member();
                    member.setId(UUID.fromString(rs.getString("id")));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setPhoneNumber(rs.getString("phoneNumber"));
                    result.put(member.getId(), member);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in searching member: " + e.getMessage());
        }

        return result;
    }
}
