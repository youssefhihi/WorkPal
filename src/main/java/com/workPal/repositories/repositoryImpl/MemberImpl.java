package com.workPal.repositories.repositoryImpl;

import com.workPal.model.Member;
import com.workPal.repositories.inteface.MemberRepository;
import com.workPal.utility.UserUtility;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MemberImpl implements MemberRepository {
    private final Connection connection;

    public MemberImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(Member member) {
        try{
            String sql = "INSERT INTO members (name, email, password, phoneNumber) VALUES (?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            String hashPassword = UserUtility.hashPassword(member.getPassword());
            statement.setString(3, hashPassword);
            statement.setString(4,member.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Error in saving member in memberImpl");
        }
    }
    @Override
    public void update(Member member){
        try{
            String sql = "UPDATE members SET name = ? , email = ?, phoneNumber = ? WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhoneNumber());
            statement.setInt(4,member.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in updating member in MemberImpl: " + e.getMessage());
        }
    }
    @Override
    public void delete (String email){
        try{
            String sql = "Delete FROM members WHERE email = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
    @Override
    public Member findMember(String email){
        Member member = null;
        try{
            String sql = "SELECT * FROM members WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setPhoneNumber(rs.getString("phoneNumber"));
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  member;
    }
    @Override
    public Map<Integer, Member> getAll() {
            Map<Integer, Member> members = new HashMap<>();
        try{
            String sql = "SELECT * FROM members";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                members.put(member.getId(),member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


}
