package ch.heigvd.utils;

import ch.heigvd.entity.*;
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
    static public SnowHeight createTestSnowHeightMeasureForTest(EntityManager em, User user) {
        return TestHelpers.createTestSnowHeightMeasure(em, user);
    }

    @Transactional
    static public BirdMigration createTestBirdMigrationForTest(EntityManager em, User user) {
        return TestHelpers.createTestBirdMigrationMeasure(em, user);
    }

    @Transactional
    static public EggsLaying createTestEggsLayingMeasureForTest(EntityManager em, User user) {
        return TestHelpers.createTestEggsLayingMeasure(em, user);
    }


}
