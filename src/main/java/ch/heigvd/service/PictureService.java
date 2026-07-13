package ch.heigvd.service;

import ch.heigvd.dto.MeasurePictureDTO;
import ch.heigvd.entity.Measure;
import ch.heigvd.entity.MeasurePicture;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@ApplicationScoped
public class PictureService {

    private final EntityManager em;

    private static final Path PICTURE_DIRECTORY = Paths.get("pictures");

    @Inject
    public PictureService(EntityManager em) {
        this.em = em;
    }

    //to get the picture file extension (.jpeg, .png, ...)
    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index);
    }

    @Transactional
    public void addPicture(FileUpload picture, Long measureId) {
        Measure measure = em.find(Measure.class, measureId);
        if (measure == null) {
            throw new NotFoundException("Measure not found");
        }
         try {
             Files.createDirectories(PICTURE_DIRECTORY);
             String fileName = "picture-" + UUID.randomUUID() + getExtension(picture.fileName());
             Path path = PICTURE_DIRECTORY.resolve(fileName);
             Files.copy(picture.uploadedFile(), path, StandardCopyOption.REPLACE_EXISTING);

             MeasurePicture measurePicture = new MeasurePicture();
             measurePicture.setMeasure(measure);
             measurePicture.setPath(path.toString());
             em.persist(measurePicture);
         } catch (Exception e) {
             throw new RuntimeException("Unable to create picture file");
         }
    }


    public MeasurePictureDTO getPictureByMeasureId(Long measureId) {

        Measure measure = em.find(Measure.class, measureId);
        if (measure == null) {
            throw new NotFoundException("Measure not found");
        }
        var query = em.createQuery("""
        SELECT p.path
        FROM MeasurePicture as p
        WHERE p.measure.id = :measureId
        """, MeasurePictureDTO.class).setParameter("measureId", measureId);

        return query.getResultList().getFirst();
    }
}
