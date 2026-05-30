package ch.heigvd.utils;

import ch.heigvd.entity.Measure;
import ch.heigvd.entity.Temperature;
import ch.heigvd.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class TestResourceHelpers {

    //Made to have the user insertion and REST test in different transactions
    //Otherwise the tests would not find the user in the database
    @Transactional
    static public User createUserForTest(EntityManager em) {
        return TestHelpers.createTestUser(em);
    }

    //Made to have the Measure insertion and REST test in different transactions
    //Otherwise the tests would not find the user in the database
    @Transactional
    static public Temperature createTestTemperatureMeasureForTest(EntityManager em, User user) {
        return TestHelpers.createTestTemperatureMeasure(em, user);
    }

    @Transactional
    static public Measure createTestSnowHeightMeasureForTest(EntityManager em, User user) {
        return TestHelpers.createTestSnowHeightMeasure(em, user);
    }

    @Transactional
    static public Measure createTestBirdMigrationForTest(EntityManager em, User user) {
        return TestHelpers.createTestBirdMigrationMeasure(em, user);
    }

    @Transactional
    static public Measure createTestEggsLayingMeasureForTest(EntityManager em, User user) {
        return TestHelpers.createTestEggsLayingMeasure(em, user);
    }


}
