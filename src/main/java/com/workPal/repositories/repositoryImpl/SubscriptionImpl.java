package com.workPal.repositories.repositoryImpl;

import com.workPal.model.*;
import com.workPal.repositories.interfaces.SubscriptionRepository;
import com.workPal.utility.Mails.JMailer;
import com.workPal.utility.Mails.MailMsg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SubscriptionImpl implements SubscriptionRepository {
    private final Connection connection;

    public SubscriptionImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Map<UUID, Subscription> getAll(){
        try{
            String sql =  """
            ELECT
            subscriptions.*,
                    spaces.id AS space_id, spaces.name AS space_name, spaces.description, spaces.location, spaces.capacity,
                    members.id AS member_id, members.name AS member_name, members.email, members.phoneNumber,
                    managers.id AS manager_id, managers.name AS manager_name, managers.email,
                    services.id AS service_id, services.name AS service_name, services.price AS service_price
            FROM subscriptions
            JOIN spaces ON subscriptions.space_id = spaces.id
            JOIN members ON subscriptions.member_id = members.id
            JOIN managers ON spaces.managers_id = managers.id
            JOIN subscription_services ON subscriptions.id = subscription_services.subscription_id
            JOIN services ON subscription_services.service_id = services.id 
            """;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            Map<UUID, Subscription> subscriptionMap = new HashMap<>();
            if (rs.next()) {
                Subscription subscription = mapResultSetToSubscription(rs);
                subscriptionMap.put(subscription.getId(),subscription);
            }
            return subscriptionMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Map<UUID, Subscription> getSubscriptionNotAccepted(Manager manager) {
        try {
            String sql = """
        SELECT
            subscriptions.*,
            spaces.id AS space_id, spaces.name AS space_name, spaces.description, spaces.location, spaces.capacity,
            members.id AS member_id, members.name AS member_name, members.email, members.phoneNumber,
            managers.id AS manager_id, managers.name AS manager_name, managers.email,
            services.id AS service_id, services.name AS service_name, services.price AS service_price
        FROM subscriptions
        JOIN spaces ON subscriptions.space_id = spaces.id
        JOIN members ON subscriptions.member_id = members.id
        JOIN managers ON spaces.manager_id = managers.id
        JOIN subscription_services ON subscriptions.id = subscription_services.subscription_id
        JOIN services ON subscription_services.service_id = services.id
        WHERE accepted = false AND spaces.manager_id = ?
        """;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, manager.getId());
            ResultSet rs = statement.executeQuery();

            Map<UUID, Subscription> subscriptionMap = new HashMap<>();
            while (rs.next()) {  // Loop to handle multiple rows
                Subscription subscription = mapResultSetToSubscription(rs);
                subscriptionMap.put(subscription.getId(), subscription);
            }

            return subscriptionMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Boolean acceptSubscription(Subscription subscription){
        try{
            String sql = "UPDATE subscriptions SET accepted = true WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, subscription.getId());
             statement.executeUpdate();
             new JMailer().sendEmail("Reservation Has Been Accepted!",MailMsg.reservationAcceptedEmail(subscription),subscription.getMember().getEmail());
            return true;
        } catch (Exception e) {
            System.out.println("error to accept subscription" + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean CreateSubscription(Subscription subscription) {
        try {
            String insertSubscriptionSql = """
                INSERT INTO subscriptions (duration, durationType, member_id, startDate, space_id)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
            """;
            PreparedStatement insertStatement = connection.prepareStatement(insertSubscriptionSql);
            insertStatement.setInt(1, subscription.getDuration());
            insertStatement.setString(2, subscription.getDurationType());
            insertStatement.setObject(3, subscription.getMember().getId());
            insertStatement.setString(4, subscription.getStartDate());
            insertStatement.setObject(5, subscription.getSpace().getId());

            ResultSet rs = insertStatement.executeQuery();
            UUID subscriptionId = null;

            if (rs.next()) {
                subscriptionId = (UUID) rs.getObject("id");
            } else {
                throw new SQLException("Creating subscription failed, no ID returned.");
            }

            String insertServiceSql = """
                INSERT INTO subscription_services (subscription_id, service_id)
                VALUES (?, ?)
            """;
            PreparedStatement serviceStatement = connection.prepareStatement(insertServiceSql);
            for (UUID serviceId : subscription.getServices().keySet()) {
                serviceStatement.setObject(1, subscriptionId);
                serviceStatement.setObject(2, serviceId);
                serviceStatement.addBatch();
            }

              serviceStatement.executeBatch();
                new JMailer().sendEmail("Reservation of co-working space", MailMsg.reservationConfirmation(subscription),subscription.getMember().getEmail());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean RemoveSubscription(Subscription subscription) {
        try {
            String deleteServicesSql = "DELETE FROM subscription_services WHERE subscription_id = ?";
            PreparedStatement deleteServiceStatement = connection.prepareStatement(deleteServicesSql);
            deleteServiceStatement.setObject(1, subscription.getId());
            deleteServiceStatement.executeUpdate();

            String deleteSubscriptionSql = "DELETE FROM subscriptions WHERE id = ?";
            PreparedStatement deleteSubscriptionStatement = connection.prepareStatement(deleteSubscriptionSql);
            deleteSubscriptionStatement.setObject(1, subscription.getId());
            int affectedRows = deleteSubscriptionStatement.executeUpdate();
            new JMailer().sendEmail("Reservation of co-working space", MailMsg.reservationConfirmation(subscription),subscription.getMember().getEmail());
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Optional<Subscription> getMemberSpace(Subscription subscription) {
        try {
            String sql = """
                SELECT
                subscriptions.*,
                        spaces.id AS space_id, spaces.name AS space_name, spaces.description, spaces.location, spaces.capacity,
                        members.id AS member_id, members.name AS member_name, members.email, members.phoneNumber,
                        managers.id AS manager_id, managers.name AS manager_name, managers.email,
                        services.id AS service_id, services.name AS service_name, services.price AS service_price
                FROM subscriptions
                JOIN spaces ON subscriptions.space_id = spaces.id
                JOIN members ON subscriptions.member_id = members.id
                JOIN managers ON spaces.manager_id = managers.id
                JOIN subscription_services ON subscriptions.id = subscription_services.subscription_id
                JOIN services ON subscription_services.service_id = services.id 
                 WHERE subscriptions.member_id  = ? AND subscriptions.space_id = ?;
        """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, subscription.getMember().getId());
            statement.setObject(2, subscription.getSpace().getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Subscription subscriptionfound = mapResultSetToSubscription(rs);
                return Optional.of(subscriptionfound);
            }

        } catch (SQLException e) {
            System.out.println("error to found member space " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Map<UUID, Subscription> getMemberSubscription(Member member) {
        try {
            String sql = """
            SELECT subscriptions.*,
                    spaces.id AS space_id, spaces.name AS space_name, spaces.description, spaces.location, spaces.capacity,
                    members.id AS member_id, members.name AS member_name, members.email, members.phoneNumber,
                    managers.id AS manager_id, managers.name AS manager_name, managers.email,
                    services.id AS service_id, services.name AS service_name, services.price AS service_price
            FROM subscriptions
            JOIN spaces ON subscriptions.space_id = spaces.id
            JOIN members ON subscriptions.member_id = members.id
            JOIN managers ON spaces.managers_id = managers.id
            JOIN subscription_services ON subscriptions.id = subscription_services.subscription_id
            JOIN services ON subscription_services.service_id = services.id 
            WHERE subscriptions.member_id = ?
        """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, member.getId());
            ResultSet rs = statement.executeQuery();

            Map<UUID, Subscription> subscriptionMap = new HashMap<>();
            while (rs.next()) {
                Subscription subscription = mapResultSetToSubscription(rs);
                subscriptionMap.put(subscription.getId(), subscription);
            }
            return subscriptionMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<UUID, Space> getSpaceSubscription(Space space) {
        try {
            String sql = """
                    SELECT subscriptions.*,
                    spaces.id AS space_id, spaces.name AS space_name, spaces.description, spaces.location, spaces.capacity,
                    members.id AS member_id, members.name AS member_name, members.email, members.phoneNumber,
                    managers.id AS manager_id, managers.name AS manager_name, managers.email,
                    services.id AS service_id, services.name AS service_name, services.price AS service_price
                    FROM subscriptions
                    JOIN spaces ON subscriptions.space_id = spaces.id
                    JOIN members ON subscriptions.member_id = members.id
                    JOIN managers ON spaces.managers_id = managers.id
                    JOIN subscription_services ON subscriptions.id = subscription_services.subscription_id
                    JOIN services ON subscription_services.service_id = services.id 
                    WHERE subscriptions.space_id = ?
            """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, space.getId());
            ResultSet rs = statement.executeQuery();

            Map<UUID, Space> spaceMap = new HashMap<>();
            while (rs.next()) {
                Subscription subscription = mapResultSetToSubscription(rs);
                spaceMap.put(subscription.getId(), subscription.getSpace());
            }
            return spaceMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private Subscription mapResultSetToSubscription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        Map<UUID, Service> services = new HashMap<>();

        // Map subscription details
        subscription.setId(UUID.fromString(rs.getString("id")));
        subscription.setDuration(rs.getInt("duration"));
        subscription.setDurationType(rs.getString("durationType"));
        subscription.setStartDate(rs.getString("startDate"));

        // Map member details
        Member member = new Member();
        member.setId(UUID.fromString(rs.getString("member_id")));
        member.setName(rs.getString("member_name"));
        member.setEmail(rs.getString("email"));
        member.setPhoneNumber(rs.getString("phoneNumber"));

        subscription.setMember(member);

        Space space = new Space();
        space.setId(UUID.fromString(rs.getString("space_id")));
        space.setName(rs.getString("space_name"));
        space.setDescription(rs.getString("description"));
        space.setLocation(rs.getString("location"));
        space.setCapacity(rs.getInt("capacity"));
        Manager manager = new Manager();
        manager.setId(UUID.fromString(rs.getString("manager_id")));
        manager.setName(rs.getString("manager_name"));
        manager.setEmail(rs.getString("email"));
        space.setManager(manager);
        subscription.setSpace(space);

        do {
            UUID serviceId = UUID.fromString(rs.getString("service_id"));
            String serviceName = rs.getString("service_name");
            Integer servicePrice = rs.getInt("service_price");

            Service service = new Service(serviceName, servicePrice);
            services.put(serviceId, service);
        } while (rs.next());

        subscription.setServices(services);

        return subscription;
    }

}
