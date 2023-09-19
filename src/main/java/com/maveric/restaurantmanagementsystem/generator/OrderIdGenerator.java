package com.maveric.restaurantmanagementsystem.generator;

import com.maveric.restaurantmanagementsystem.config.AppConstants;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.jdbc.Work;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {

        String query = AppConstants.QUERY_GENERATE_ORDER_ID;
        Session currentSession = (Session) session;

        final String[] generatedId = new String[1];

        currentSession.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        int nextValue = rs.getInt(1) + 1;
                        generatedId[0] = AppConstants.ORDER_PREFIX + String.format(AppConstants.ORDER_SUFFIX_FORMAT, nextValue);
                    } else {
                        generatedId[0] = AppConstants.ORDER_PREFIX + AppConstants.ORDER_SUFFIX;
                    }
                }
            }
        });

        return generatedId[0];
    }
}
