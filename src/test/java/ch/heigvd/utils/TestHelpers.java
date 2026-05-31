package ch.heigvd.utils;

import ch.heigvd.entity.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.UUID;

public class TestHelpers {

    /**
     * Create a default User for test methods
     * @param em the EntityManager of the test
     * @return the test user
     */
    public static User createTestUser(EntityManager em) {
        User user = new User();

        user.setName("Test User");
        //generate different random email to be sure there is no compatibility problem
        user.setEmail(UUID.randomUUID() + "@test.ch");
        user.setPassword(BcryptUtil.bcryptHash("password"));
        user.setValid(true);
        user.setAdmin(false);
        user.setRole("");

        em.persist(user);
        return user;
    }

    /**
     * Create a default Temperature for test methods
     * @param em the EntityManager of the test
     * @param user a test user
     * @return the test temperature measure
     */
    public static Temperature createTestTemperatureMeasure(EntityManager em, User user) {

        Temperature temperature = new Temperature();
        temperature.setDate(LocalDate.of(2001, 2, 15));
        temperature.setLocation("testlocation");
        temperature.setDegree(30);
        temperature.setType(MeasureType.TEMPERATURE);
        temperature.setUser(user);
        user.getMeasures().add(temperature);

        em.persist(temperature);
        return temperature;
    }

    /**
     * Create a default SnowHeight for test methods
     * @param em the EntityManger of the test
     * @param user a test user
     * @return the test SnowHeight measure
     */
    public static SnowHeight createTestSnowHeightMeasure(EntityManager em, User user) {

        SnowHeight snowHeight = new SnowHeight();
        snowHeight.setDate(LocalDate.of(2001, 2, 15));
        snowHeight.setLocation("testlocation");
        snowHeight.setHeight(10);
        snowHeight.setWeather("Sunny");
        snowHeight.setPrecipitation(3);
        snowHeight.setType(MeasureType.SNOW_HEIGHT);
        snowHeight.setUser(user);
        user.getMeasures().add(snowHeight);


        em.persist(snowHeight);
        return snowHeight;
    }

    /**
     * Create a default BirdMigration for test methods
     * @param em the EntityManager of the test
     * @param user a test user
     * @return the test BirdMigration measure
     */
    public static BirdMigration createTestBirdMigrationMeasure(EntityManager em, User user) {

        BirdMigration birdMigration = new BirdMigration();
        birdMigration.setDate(LocalDate.of(2001, 2, 15));
        birdMigration.setLocation("testlocation");
        birdMigration.setBirdType("Pigeon");
        birdMigration.setArrival(true);
        birdMigration.setType(MeasureType.BIRD_MIGRATION);
        birdMigration.setUser(user);
        user.getMeasures().add(birdMigration);


        em.persist(birdMigration);
        return birdMigration;
    }

    /**
     * Create a default EggsLaying for test methods
     * @param em the EntityManager of the test
     * @param user a test user
     * @return the test EggsLaying measure
     */
    public static EggsLaying createTestEggsLayingMeasure(EntityManager em, User user) {

        EggsLaying eggsLaying = new EggsLaying();
        eggsLaying.setDate(LocalDate.of(2001, 2, 15));
        eggsLaying.setLocation("testlocation");
        eggsLaying.setNumber(10);
        eggsLaying.setType(MeasureType.EGGS_LAYING);
        eggsLaying.setUser(user);
        user.getMeasures().add(eggsLaying);


        em.persist(eggsLaying);
        return eggsLaying;
    }
}
